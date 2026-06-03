package model;

import pathfinder.PathFinder;

import java.awt.*;
import java.util.List;


public abstract class Creature extends Entity {

    private final int speed;
    private int hp;
    private Point position;
    private final int maxHp;
    private final PathFinder pathFinder;

    public Creature(int speed, int hp, int maxHp, Point position, PathFinder pathFinder) {
        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
        this.position = position;
        this.pathFinder = pathFinder;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.clamp(hp, 0, this.maxHp);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void makeMove(GameMap map){
        move(map);
    }

    protected abstract boolean isTarget(Entity entity);

    abstract Class<? extends Entity> getTargetClass();

    abstract void interactWithTarget(Entity target, GameMap map, Point position);

    protected abstract void onNoPath(GameMap map);

    private void interactIfTarget(GameMap map) {
        map.getEntityAt(getPosition()).filter(this::isTarget).ifPresent(target -> interactWithTarget(target,
                map,
                getPosition()));
    }

    private boolean tryInteractAt(GameMap map, Point position) {
        return map.getEntityAt(position).filter(this::isTarget).map(target -> {
            interactWithTarget(target, map, position);
            return true;
        }).orElse(false);
    }

    protected void move(GameMap map) {

        List<Point> path = pathFinder.findPath(map, getPosition(), getTargetClass());
        if (path.isEmpty()) {
            onNoPath(map);
            return;
        }
        if (path.size() == 1) {
            interactIfTarget(map);
            return;
        }
        Point nextStep = path.get(1);
        if (tryInteractAt(map,nextStep)){
            return;
        }
        if (map.getEntityAt(getPosition()).orElse(null)!=this){
            return;
        }
        map.removeEntity(getPosition());
        setPosition(nextStep);
        map.addEntity(getPosition(), this);
        interactIfTarget(map);
    }
}

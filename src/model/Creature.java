package model;

import pathfinder.PathFinder;

import java.awt.*;

public abstract class Creature extends Entity {

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

    private final int speed;
    private int hp;
    private Point position;
    private final int maxHp;


    public Creature(int speed, int hp, int maxHp, Point position) {

        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
        this.position = position;
    }

    public abstract void makeMove(GameMap map);

    abstract boolean isTarget(Entity entity);

    abstract Class<? extends Entity> getTargetClass();

    abstract void interactWithTarget(Entity target, GameMap map, Point position);

    protected void onNoPath(GameMap map) {
    }

    protected void move(GameMap map) {
        Point currentPos = this.getPosition();
        Entity entity = map.getEntityAt(currentPos);
        if (isTarget(entity)) {
            interactWithTarget(entity, map, currentPos);
            return;
        }
        int[] x = {-1, 1, 0, 0};
        int[] y = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = currentPos.x + x[i];
            int ny = currentPos.y + y[i];
            Point neighbor = new Point(nx, ny);
            Entity neighborEntity = map.getEntityAt(neighbor);
            if (isTarget(neighborEntity)) {
                interactWithTarget(neighborEntity, map, neighbor);
                return;
            }
        }
        PathFinder pathFinder = new PathFinder();
        Point nextStep = pathFinder.findNextStep(map, currentPos, getTargetClass());
        if (nextStep != null) {
            map.removeEntity(currentPos);
            setPosition(nextStep);
            map.addEntity(getPosition(), this);
        } else {
            onNoPath(map);
        }
    }
}

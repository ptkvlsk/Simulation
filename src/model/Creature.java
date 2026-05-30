package model;

import pathfinder.PathFinder;
import util.Direction;
import util.PassabilityChecker;

import java.awt.*;
import java.util.List;
import java.util.Optional;


public abstract class Creature extends Entity {

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

    public abstract void makeMove(GameMap map);

    abstract boolean isTarget(Entity entity);

    abstract Class<? extends Entity> getTargetClass();

    abstract void interactWithTarget(Entity target, GameMap map, Point position);

    protected void onNoPath(GameMap map) {
    }

    protected void move(GameMap map) {
        Point currentPos = this.getPosition();

        Optional<Entity> optEntity = map.getEntityAt(currentPos);
        if (optEntity.isPresent() && isTarget(optEntity.get())) {
            interactWithTarget(optEntity.get(), map, currentPos);
            return;
        }

        for (Point offset : Direction.NEIGHBOR_OFFSETS) {
            Point neighbor = new Point(currentPos.x + offset.x, currentPos.y + offset.y);
            if (neighbor.x >= 0 && neighbor.x < map.getWidth()
                    && neighbor.y >= 0 && neighbor.y < map.getHeight()) {
                Optional<Entity> optNeighbor = map.getEntityAt(neighbor);
                if (optNeighbor.isPresent() && isTarget(optNeighbor.get())) {
                    interactWithTarget(optNeighbor.get(), map, neighbor);
                    return;
                }
            }
        }

        PassabilityChecker checker = new PassabilityChecker();
        PathFinder pathFinder = new PathFinder(checker);
        List<Point> path = pathFinder.findPath(map, currentPos, getTargetClass());
        if (path.size() > 1) {
            Point nextStep = path.get(1);
            Optional<Entity> optTarget = map.getEntityAt(nextStep);
            if (optTarget.isPresent() && isTarget(optTarget.get())) {
                interactWithTarget(optTarget.get(), map, nextStep);
                return;
            }
            map.removeEntity(currentPos);
            setPosition(nextStep);
            if (!map.isCellEmpty(nextStep)) {
                Optional<Entity> optObstacle = map.getEntityAt(nextStep);
                String obstacle = optObstacle.map(Object::toString).orElse("unknown");
                throw new IllegalStateException("Next step " + nextStep + " is not empty, contains: " + obstacle);
            }
            map.addEntity(getPosition(), this);
        } else {
            onNoPath(map);
        }
    }
}

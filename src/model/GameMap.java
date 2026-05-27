package model;

import java.awt.*;
import java.util.*;
import java.util.List;


public class GameMap {

    private final int width;
    private final int height;
    private Map<Point, Entity> pointWithEntities = new HashMap<>();

    public GameMap(int width, int height) {

        this.width = width;
        this.height = height;
        pointWithEntities = new HashMap<>();
    }

    private void validate(Point position) {
        if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
    }

    public void addEntity(Point position, Entity entity) {
        validate(position);
        if (!isCellEmpty(position)) {
            throw new IllegalStateException("Cell already occupied: " + position);
        }
        pointWithEntities.put(position, entity);

        ;
    }

    public void removeEntity(Point position) {
        validate(position);
        if (!pointWithEntities.containsKey(position)) {
            throw new IllegalStateException("No entity at position: " + position);
        }
        pointWithEntities.remove(position);
    }

    public Optional<Entity> getEntityAt(Point position) {
        validate(position);
        return Optional.ofNullable(pointWithEntities.get(position));
    }

    public boolean isCellEmpty(Point position) {
        validate(position);
        return !pointWithEntities.containsKey(position);
    }

    public <T extends Entity> List<T> getEntitiesBy(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Entity entity : pointWithEntities.values()) {
            if (type.isInstance(entity)) {
                result.add((T) entity);
            }
        }
        return result;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

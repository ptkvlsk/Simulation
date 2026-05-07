import java.awt.*;
import java.util.*;
import java.util.List;

public class Map {
    private Map<Point, Entity> map = new HashMap<>();

    void addEntity(Point position, Entity entity) {
        if (isCellEmpty(position) == true) {
    map.put(position,entity);
        }
    }

    void removeEntity(Point position) {
if (!isCellEmpty(position)==true){
    map.remove(position);
}
    }

    public Entity getEntityAt(Point position) {
        return map.get(position);
    }

    public boolean isCellEmpty(Point position) {
        return !map.containsKey(position);
    }

    public List<Herbivore> getAllHerbivores() {
        List<Herbivore> result = new ArrayList<>();
        for (Entity entity : map.values()) {
            if (entity instanceof Herbivore) {
                result.add((Herbivore) entity);
            }
        }
        return result;
    }

    public List<Predator> getAllPredators() {
        List<Predator> result = new ArrayList<>();
        for (Entity entity : map.values()) {
            if (entity instanceof Predator) {
                result.add((Predator) entity);
            }
        }
        return result;
    }

    public Set<Point> getAllGrassPositions() {
        Set<Point> result = new HashSet<>();
        for (Map.Entry<Point, Entity> entry : map.entrySet()) {
            if (entry.getValue() instanceof Grass) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}

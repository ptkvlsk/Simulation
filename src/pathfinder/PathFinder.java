package pathfinder;

import constant.Direction;
import model.*;

import java.awt.*;
import java.util.*;

import static constant.Direction.DX;
import static constant.Direction.DY;

public class PathFinder {

    public Point findNextStep(GameMap map, Point start, Class<? extends Entity> targetType) {
        Queue<Point> queue = new LinkedList<Point>();
        Set<Point> visited = new HashSet<Point>();
        Map<Point, Point> previous = new HashMap<Point, Point>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Optional<Entity> optEntity = map.getEntityAt(current);

            for (int i = 0; i < Direction.COUNT; i++) { // magic
                int nx = current.x + DX[i];
                int ny = current.y + DY[i];
                Point neighbor = new Point(nx, ny);
                int width = map.getWidth();
                int height = map.getHeight();
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited.contains(neighbor) && isPassible(map, neighbor, targetType)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
            if (optEntity.isPresent() && targetType.isInstance(optEntity.get())) {
                Point step = current;
                Point prev = current;
                while (prev != start) {
                    step = prev;
                    prev = previous.get(prev);
                }
                return step;
            }
        }
        return null;
    }

    private boolean isPassible(GameMap map, Point position, Class<?> targetType) {
        Optional<Entity> optEntity = map.getEntityAt(position);
        if (optEntity.isEmpty()) return true;

        Entity entity = optEntity.get();
        if (targetType.isInstance(entity)) return true;
        if (entity instanceof Predator) return false;
        if (entity instanceof Grass) return false;
        if (entity instanceof Herbivore) return false;
        return !(entity instanceof Rock) && !(entity instanceof Tree);
    }
}

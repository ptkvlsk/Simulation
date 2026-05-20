package pathfinder;

import model.*;

import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

public class PathFinder {

    public Point findNextStep(GameMap map, Point start, Class<? extends Entity> targetType) {
        Queue<Point> queue = new LinkedList<Point>();
        Set<Point> visited = new HashSet<Point>();
        Map<Point, Point> previous = new HashMap<Point, Point>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Entity entity = map.getEntityAt(current);
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) { // magic
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];
                Point neighbor = new Point(nx, ny);
                int width = map.getWidth();
                int height = map.getHeight();
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited.contains(neighbor) && isPassible(map, neighbor, targetType)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
            if (targetType.isInstance(entity)) {
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
        Entity entity = map.getEntityAt(position);
        if (entity == null) {
            return true;
        }
        if (targetType.isInstance(entity)) {
            return true;
        }
        if (entity instanceof Predator) {
            return false;
        }
        if (entity instanceof Grass) {
            return false;
        }
        return !(entity instanceof Rock) && !(entity instanceof Tree);
    }
}

package pathfinder;

import utill.Direction;
import model.*;
import utill.PassabilityChecker;

import java.awt.*;
import java.util.*;
import java.util.List;

import static utill.Direction.DX;
import static utill.Direction.DY;

public class PathFinder {
    public PathFinder(PassabilityChecker passabilityChecker) {
        this.passabilityChecker = passabilityChecker;
    }

    public List<Point> findPath(GameMap map, Point start, Class<? extends Entity> targetType) {
        Queue<Point> queue = new LinkedList<Point>();
        Set<Point> visited = new HashSet<Point>();
        Map<Point, Point> previous = new HashMap<Point, Point>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Optional<Entity> optEntity = map.getEntityAt(current);
            if (optEntity.isPresent() && targetType.isInstance(optEntity.get())) {
                List<Point> path = new ArrayList<>();
                Point point = current;
                while (point != start) {
                    path.add(point);
                    point = previous.get(point);
                }
                path.add(0, start);
                return path;


            }
            for (int i = 0; i < Direction.COUNT; i++) {
                int nx = current.x + DX[i];
                int ny = current.y + DY[i];
                if (nx >= 0 && nx < map.getWidth() && ny >= 0 && ny < map.getHeight()) {
                    Point neighbor = new Point(nx, ny);
                    if (!visited.contains(neighbor) && passabilityChecker.isPassible(map,neighbor,targetType)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        previous.put(neighbor, current);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
private final PassabilityChecker passabilityChecker;

}

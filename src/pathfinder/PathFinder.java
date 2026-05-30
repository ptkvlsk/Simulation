package pathfinder;

import util.Direction;
import model.*;
import util.PassabilityChecker;

import java.awt.*;
import java.util.*;
import java.util.List;


public class PathFinder {
    public PathFinder(PassabilityChecker passabilityChecker) {
        this.passabilityChecker = passabilityChecker;
    }

    public List<Point> findPath(GameMap map, Point start, Class<? extends Entity> targetType) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        Map<Point, Point> previous = new HashMap<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Optional<Entity> optEntity = map.getEntityAt(current);
            if (optEntity.isPresent() && targetType.isInstance(optEntity.get())) {
                List<Point> path = new ArrayList<>();
                Point point = current;
                while (point != start) {
                    path.add(0, point);
                    point = previous.get(point);
                }
                path.add(0, start);
                return path;
            }
            for (Point offset : Direction.NEIGHBOR_OFFSETS) {
                Point neighbor = new Point(current.x + offset.x, current.y + offset.y);
                boolean isWithBound = neighbor.x >= 0 && neighbor.x < map.getWidth()
                        && neighbor.y >= 0 && neighbor.y < map.getHeight();
                if (isWithBound){
                    if (!visited.contains(neighbor)&&passabilityChecker.isPassible(map,neighbor,targetType)){
                        queue.add(neighbor);
                        visited.add(neighbor);
                        previous.put(neighbor,current);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private final PassabilityChecker passabilityChecker;

}

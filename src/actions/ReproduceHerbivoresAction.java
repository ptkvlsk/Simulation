package actions;

import util.Direction;
import model.Entity;
import model.GameMap;
import model.Herbivore;
import model.Predator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class ReproduceHerbivoresAction implements Action {
    private final int maxHerbivores;
    private final double hpThreshold;
    private static final double BASE_CHANCE = 1.0;
    private final Random random = new Random();


    public ReproduceHerbivoresAction(int maxHerbivores, double hpThreshold) {
        this.maxHerbivores = maxHerbivores;
        this.hpThreshold = hpThreshold;
    }

    @Override
    public void execute(GameMap map) {

        List<Herbivore> herbivores = map.getEntitiesBy(Herbivore.class);
        int currentCount = herbivores.size();
        for (Herbivore parent : herbivores) {
            if (currentCount >= maxHerbivores) {
                break;
            }
            if (parent.getHp() > parent.getMaxHp() * hpThreshold) {
                if (hasPredatorNearby(parent, map)) {
                    continue;
                }
                List<Point> emptyNeighbors = findEmptyNeighbors(parent, map);
                if (emptyNeighbors.isEmpty()) {
                    continue;
                }
                Point spawnPoint = emptyNeighbors.get(random.nextInt(emptyNeighbors.size()));
                if (!shouldReproduce(currentCount, maxHerbivores, random)) {
                    continue;
                }
                createChild(parent, spawnPoint, map);
                currentCount++;
            }
        }
    }

    private boolean hasPredatorNearby(Herbivore parent, GameMap map) {
        boolean hasPredator = false;
        for (Point offset : Direction.NEIGHBOR_OFFSETS) {
            Point neighbor = new Point(parent.getPosition().x + offset.x,
                    parent.getPosition().y + offset.y);
            if (neighbor.x >= 0 && neighbor.x < map.getWidth() && neighbor.y >= 0 && neighbor.y < map.getHeight()) {
                Optional<Entity> optEntity = map.getEntityAt(neighbor);
                if (optEntity.isPresent() && optEntity.get() instanceof Predator) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Point> findEmptyNeighbors(Herbivore parent, GameMap map) {
        List<Point> emptyNeighbors = new ArrayList<>();
        for (Point offset : Direction.NEIGHBOR_OFFSETS) {
            Point neighbor = new Point(parent.getPosition().x + offset.x, parent.getPosition().y + offset.y);
            if (neighbor.x >= 0 && neighbor.x < map.getWidth() && neighbor.y >= 0 && neighbor.y < map.getHeight()) {
                if (map.isCellEmpty(neighbor)) {
                    emptyNeighbors.add(neighbor);
                }
            }
        }
        return emptyNeighbors;
    }

    private boolean shouldReproduce(int currentCount, int maxHerbivores, Random random) {
        double chance = BASE_CHANCE - (currentCount / (double) maxHerbivores);
        return random.nextDouble() < chance;
    }

    private void createChild(Herbivore parent, Point spawnPoint, GameMap map) {
        Herbivore child = new Herbivore(parent.getSpeed(), parent.getMaxHp() / 2, parent.getMaxHp(), spawnPoint);
        map.addEntity(spawnPoint, child);
        parent.setHp(parent.getHp() / 2);
    }
}


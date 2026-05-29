package actions;

import utill.Direction;
import model.Entity;
import model.GameMap;
import model.Herbivore;
import model.Predator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static utill.Direction.DX;
import static utill.Direction.DY;

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
                boolean hasPredator = false;
                for (int i = 0; i < Direction.COUNT; i++) {
                    int nx = parent.getPosition().x + DX[i];
                    int ny = parent.getPosition().y + DY[i];
                    if (nx >= 0 && nx < map.getWidth() && ny >= 0 && ny < map.getHeight()) {
                        Point neighbor = new Point(nx, ny);
                        Optional<Entity> optEntity = map.getEntityAt(neighbor);
                        if (optEntity.isPresent()&& optEntity.get() instanceof Predator) {
                            hasPredator = true;
                            break;
                        }
                    }
                }
                if (hasPredator) {
                    continue;
                }
                ArrayList<Point> emptyNeighbors = new ArrayList<>();
                for (int i = 0; i < Direction.COUNT; i++) {
                    int nx = parent.getPosition().x + DX[i];
                    int ny = parent.getPosition().y + DY[i];
                    if (nx >= 0 && nx < map.getWidth() && ny >= 0 && ny < map.getHeight()) {
                        Point neighbor = new Point(nx, ny);
                        if (map.isCellEmpty(neighbor)) {
                            emptyNeighbors.add(neighbor);
                        }
                    }

                }
                if (emptyNeighbors.isEmpty()) {
                    continue;
                } else {
                    Point spawnPoint = emptyNeighbors.get(random.nextInt(emptyNeighbors.size()));
                    double chance = BASE_CHANCE - (currentCount / (double) maxHerbivores);
                    if (random.nextDouble() >= chance) {
                        continue;
                    }
                    Herbivore child = new Herbivore(parent.getSpeed(), parent.getMaxHp() / 2, parent.getMaxHp(), spawnPoint);
                    map.addEntity(spawnPoint, child);
                    parent.setHp(parent.getHp() / 2);
                }
                currentCount++;
            }
        }
    }
}

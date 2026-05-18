package actions;

import model.Entity;
import model.GameMap;
import model.Herbivore;
import model.Predator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReproduceHerbivoresAction implements Action {
    private final int maxHerbivores;
    private final double hpThreshold;
    private static final double BASE_CHANCE =1.0;
    private static final int DIRECTION_COUNT = 4;
    private final Random random = new Random();


    public ReproduceHerbivoresAction(int maxHerbivores, double hpThreshold) {
        this.maxHerbivores = maxHerbivores;
        this.hpThreshold = hpThreshold;
    }

    @Override
    public void execute(GameMap map) {

        List<Herbivore> herbivores = map.getAllHerbivores();
        int currentCount = map.getAllHerbivores().size();
        for (Herbivore parent : herbivores) {
            if (currentCount >= maxHerbivores) {
                break;
            }
            if (parent.getHp() > parent.getMaxHp() * hpThreshold) {
                int[] dx = {-1, 1, 0, 0};
                int[] dy = {0, 0, -1, 1};
                boolean hasPredator = false;
                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nx = parent.getPosition().x + dx[i];
                    int ny = parent.getPosition().y + dy[i];
                    Point neighbor = new Point(nx, ny);
                    Entity entity = map.getEntityAt(neighbor);
                    if (entity instanceof Predator) {
                        hasPredator = true;
                        break;
                    }
                }
                if (hasPredator) {
                    continue;
                }
                ArrayList<Point> emptyNeighbors = new ArrayList<>();
                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nx = parent.getPosition().x + dx[i];
                    int ny = parent.getPosition().y + dy[i];
                    Point neighbor = new Point(nx, ny);
                    if (map.isCellEmpty(neighbor)) {
                        emptyNeighbors.add(neighbor);
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

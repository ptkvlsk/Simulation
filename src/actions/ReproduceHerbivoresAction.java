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


    @Override
    public void execute(GameMap map) {
        List<Herbivore> herbivores = map.getAllHerbivores();
        int currentCount = map.getAllHerbivores().size();
        for (Herbivore parent : herbivores) {
            if (currentCount >= 22) {
                break;
            }
            if (parent.getHp() > parent.getMaxHp() * 0.85) {
                int[] dx = {-1, 1, 0, 0};
                int[] dy = {0, 0, -1, 1};
                boolean hasPredator = false;
                for (int i = 0; i < 4; i++) {
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
                for (int i = 0; i < 4; i++) {
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
                    Random random = new Random();
                    Point spawnPoint = emptyNeighbors.get(random.nextInt(emptyNeighbors.size()));
                    Herbivore child = new Herbivore(parent.getSpeed(), parent.getMaxHp() / 2, parent.getMaxHp(), spawnPoint);
                    map.addEntity(spawnPoint, child);
                    parent.setHp(parent.getHp()/2);
                }
                currentCount++;
            }

        }
    }
}

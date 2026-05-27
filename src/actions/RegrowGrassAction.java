package actions;

import model.GameMap;
import model.Grass;

import java.awt.*;
import java.util.Random;

public class RegrowGrassAction implements Action {

    private final int targetGrassCount;

    public RegrowGrassAction(int targetGrassCount) {
        this.targetGrassCount = targetGrassCount;
    }

    @Override
    public void execute(GameMap map) {
        int currentGrass = map.getEntitiesBy(Grass.class).size();
        if (currentGrass < targetGrassCount) {
            int toAdd = targetGrassCount - currentGrass;
            int maxAttempts = map.getWidth() * map.getHeight() * 2;
            Random random = new Random();
            while (toAdd > 0 && maxAttempts > 0) {
                int x = random.nextInt(map.getWidth());
                int y = random.nextInt(map.getHeight());
                Point point = new Point(x, y);
                if (map.isCellEmpty(point)) {
                    Grass grass = new Grass();
                    map.addEntity(point, grass);
                    toAdd--;
                }
                maxAttempts--;
            }
        }
    }
}

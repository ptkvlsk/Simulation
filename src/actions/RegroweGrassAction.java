package actions;

import model.GameMap;
import model.Grass;

import java.awt.*;
import java.util.Random;

public class RegroweGrassAction implements Action {
    int targetGrassCount;

    public RegroweGrassAction(int targetGrassCount) {
        this.targetGrassCount = targetGrassCount;
    }

    @Override
    public void execute(GameMap map) {
        int currentGrass = map.getAllGrassPositions().size();
        if (currentGrass < targetGrassCount) {
            int toAdd = targetGrassCount - currentGrass;
            int maxAttempts = 1000;
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

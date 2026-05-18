package actions;

import model.GameMap;
import model.Grass;

import java.awt.*;
import java.util.Random;

public class SpawnGrassAction implements Action {
    private int count;
    private int width;
    private int height;

    public SpawnGrassAction(int count, int width, int height) {
        this.count = count;
        this.width = width;
        this.height = height;
    }

    @Override
    public void execute(GameMap map) {
        int maxAttempts = 1000;
        Random random = new Random();
        int spawned = 0;
        while (spawned < count && maxAttempts > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Point point = new Point(x, y);
            if (map.isCellEmpty(point)) {
                Grass grass = new Grass();
                map.addEntity(point, grass);
                spawned++;
                maxAttempts--;
            }
        }
    }
}

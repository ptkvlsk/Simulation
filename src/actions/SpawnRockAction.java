package actions;

import model.GameMap;
import model.Rock;

import java.awt.*;
import java.util.Random;

public class SpawnRockAction implements Action {
    private int count;
    private int width;
    private int height;

    public SpawnRockAction(int count, int width, int height) {
        this.count = count;
        this.width = width;
        this.height = height;

    }

    @Override
    public void execute(GameMap map) {
        Random random = new Random();
        int spawned = 0;
        int maxAttempts = 1000;
        while (spawned < count && maxAttempts > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Point point = new Point(x, y);
            if (map.isCellEmpty(point)) {
                Rock rock = new Rock();
                map.addEntity(point, rock);
                spawned++;
            }
            maxAttempts--;
        }
    }
}

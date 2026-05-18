package actions;

import model.GameMap;
import model.Tree;

import java.awt.*;
import java.util.Random;

public class SpawnTreesAction implements Action {
    private int count;
    private int width;
    private int height;

    public SpawnTreesAction(int count, int width, int height) {
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
                Tree tree = new Tree();
                map.addEntity(point, tree);
                spawned++;
            }
            maxAttempts--;
        }
    }
}

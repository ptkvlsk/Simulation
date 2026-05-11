package actions;

import model.GameMap;
import model.Predator;

import java.awt.*;
import java.util.Random;

public class SpawnPredatorsAction implements Action {
    int count;
    int width;
    int height;
    int speed;
    int hp;
    int attackPower;

    public SpawnPredatorsAction(int count, int width, int height, int speed, int hp, int attackPower) {
        this.count = count;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    @Override
    public void execute(GameMap map) {
        int maxAttempts = 1000;
        Random random = new Random(maxAttempts);
        int spawned = 0;
        while (spawned < count && maxAttempts > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Point point = new Point(x, y);
            if (map.isCellEmpty(point)) {
                Predator predator = new Predator(this.speed, this.hp, this.attackPower, point);
                map.addEntity(point, predator);
                spawned++;
                maxAttempts--;
            }
        }
    }
}

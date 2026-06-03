package actions;

import model.GameMap;
import model.Predator;
import pathfinder.PathFinder;

import java.awt.*;
import java.util.Random;

public class SpawnPredatorsAction implements Action {

    private static final int ATTEMPTS_MULTIPLIER = 2;
    private final int count;
    private final int width;
    private final int height;
    private final int speed;
    private final int hp;
    private final int attackPower;
    private final int maxHp;
    private final PathFinder pathFinder;

    public SpawnPredatorsAction(int count, int width, int height, int speed, int hp, int maxHp, int attackPower, PathFinder pathFinder) {

        this.count = count;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
        this.attackPower = attackPower;
        this.maxHp = maxHp;
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute(GameMap map) {
        int maxAttempts = width * height * ATTEMPTS_MULTIPLIER;
        Random random = new Random(); // можно в экшн
        int spawned = 0;
        while (spawned < count && maxAttempts > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Point point = new Point(x, y);
            if (map.isCellEmpty(point)) {
                Predator predator = new Predator(this.speed, this.hp, this.attackPower, this.maxHp, point, pathFinder);
                map.addEntity(point, predator);
                spawned++;
                maxAttempts--;
            }
        }
    }
}

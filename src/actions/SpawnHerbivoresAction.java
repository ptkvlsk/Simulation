package actions;

import model.GameMap;
import model.Herbivore;

import java.awt.*;
import java.util.Random;

public class SpawnHerbivoresAction implements Action {
    private int count;
    private int width;
    private int height;
    private int speed;
    private int hp;
    private int maxHp;

    public SpawnHerbivoresAction(int count, int width, int height, int speed, int hp, int maxHp) {
        this.count = count;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
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
                Herbivore herbivore = new Herbivore(this.speed, this.hp, this.maxHp, point);
                map.addEntity(point, herbivore);
                spawned++;
                maxAttempts--;
            }
        }
    }
}

package actions;

import model.GameMap;
import model.Herbivore;

import java.awt.*;
import java.util.Random;

public class SpawnHerbivoresAction implements Action {
    int count;
    int width;
    int height;
    int speed;
    int hp;

    public SpawnHerbivoresAction(int count, int width, int height, int speed, int hp) {
        this.count = count;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
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
                Herbivore herbivore = new Herbivore(this.speed,this.hp,point);
                map.addEntity(point, herbivore);
                spawned++;
                maxAttempts--;
            }
        }
    }
}

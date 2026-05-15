package model;

import java.awt.*;

public abstract class Creature extends Entity {
    public int speed;
    public int hp;
    public Point position;
    public int maxHp;


    public Creature(int speed, int hp, int maxHp, Point position) {
        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
        this.position = position;
    }

    public abstract void makeMove(GameMap map);
}

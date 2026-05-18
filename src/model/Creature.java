package model;

import java.awt.*;

public abstract class Creature extends Entity {
    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, this.maxHp));
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public int getMaxHp() {
        return maxHp;
    }

    private int speed;
    private int hp;
    private Point position;
    private int maxHp;


    public Creature(int speed, int hp, int maxHp, Point position) {
        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
        this.position = position;
    }

    public abstract void makeMove(GameMap map);
}

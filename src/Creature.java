import java.awt.*;

public abstract class Creature extends Entity {
    protected int speed;
    protected int hp;
    protected Point position;


    public Creature(int speed, int hp, Point position) {
        this.speed = speed;
        this.hp = hp;
        this.position = position;
    }

    public abstract void makeMove(GameMap map);
}

public abstract class Creature extends Entity {
    protected int speed;
    protected int hp;

    public Creature(int speed, int hp) {
        this.speed = speed;
        this.hp = hp;
    }

    public abstract void makeMove(Map map);
}

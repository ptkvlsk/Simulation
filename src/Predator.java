public class Predator extends Creature {
    protected int attackPower;

    public Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
        this.attackPower = attackPower;

    }

    @Override
    public void makeMove(Map map) {

    }
}

import java.awt.*;

public class Herbivore extends Creature {
    public Herbivore(int speed, int hp, Point position) {
        super(speed, hp, position);
    }

    public void ateGrass() {

    }

    @Override
    public void makeMove(GameMap map) {
        Point currentPos = this.position;
        if (map.getEntityAt(currentPos) instanceof Grass) {
            map.removeEntity(currentPos);
            this.hp += 2;
            return;
        }
        PathFinder pathFinder = new PathFinder();
        Point nextStep = pathFinder.findNextStep(map, currentPos, Grass.class);
        if (nextStep != null) {
            map.removeEntity(currentPos);
            position = nextStep;
            map.addEntity(position, this);
        }
    }

    public void takeDamage(int attackPower) {
        hp -= attackPower;
    }
}

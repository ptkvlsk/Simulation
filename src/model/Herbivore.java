package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Herbivore extends Creature {
    public Herbivore(int speed, int hp, Point position) {
        super(speed, hp, position);
    }

    public void ateGrass() {

    }

    @Override
    public void makeMove(GameMap map) {
        System.out.println("makeMove called for herbivore at " + position);
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
            System.out.println("Next step: " + nextStep);
        }
        System.out.println("MakeMove called for herbivore at " + position);
    }

    public void takeDamage(int attackPower) {
        hp -= attackPower;
    }
}

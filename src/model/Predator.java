package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Predator extends Creature {
    protected int attackPower;

    public Predator(int speed, int hp, int attackPower, Point position) {
        super(speed, hp, position);
        this.attackPower = attackPower;

    }

    @Override
    public void makeMove(GameMap map) {
        Point nextStep = null;
        Point currentPos = this.position;
        if (map.getEntityAt(currentPos) instanceof Herbivore) {
            Herbivore target = (Herbivore) map.getEntityAt(currentPos);
            target.takeDamage(this.attackPower);
            if (target.hp <= 0) {
                map.removeEntity(currentPos);
            }
        } else {
            PathFinder pathFinder = new PathFinder();
            nextStep = pathFinder.findNextStep(map, currentPos, Herbivore.class);
        }
        if (nextStep != null) {
            map.removeEntity(currentPos);
            position = nextStep;
            map.addEntity(position, this);
        }

    }
}

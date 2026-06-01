package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Predator extends Creature {

    public int getAttackPower() {
        return attackPower;
    }

    private final int attackPower;

    public Predator(int speed, int hp, int maxHp, int attackPower, Point position, PathFinder pathFinder) {

        super(speed, hp, maxHp, position, pathFinder);
        this.attackPower = attackPower;

    }

    @Override
    protected boolean isTarget(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected Class<? extends Entity> getTargetClass() {
        return Herbivore.class;
    }

    @Override
    protected void interactWithTarget(Entity target, GameMap map, Point position) {
        if (target instanceof Herbivore herbivore) {
            herbivore.takeDamage(getAttackPower());
            if (herbivore.getHp() <= 0) {
                map.removeEntity(position);
            }
        }
    }

    @Override
    protected void onNoPath(GameMap map) {
    }

    @Override
    public void makeMove(GameMap map) {
        move(map);
    }
}

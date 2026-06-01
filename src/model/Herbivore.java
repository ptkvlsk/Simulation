package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Herbivore extends Creature {
    private static final int GRASS_HEAL_AMOUNT = 2;
    private static final int HUNGER_DAMAGE = 1;

    public Herbivore(int speed, int hp, int maxHp, Point position, PathFinder pathFinder) {
        super(speed, hp, maxHp, position, pathFinder);
    }


    @Override
    protected boolean isTarget(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    protected void interactWithTarget(Entity target, GameMap map, Point position) {
        map.removeEntity(position);
        setHp(getHp() + GRASS_HEAL_AMOUNT);
    }

    @Override
    protected void onNoPath(GameMap map) {
        setHp(getHp() - HUNGER_DAMAGE);
    }

    public void takeDamage(int attackPower) {
        setHp(getHp() - attackPower);
    }
}

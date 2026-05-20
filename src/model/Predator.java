package model;

import java.awt.*;

public class Predator extends Creature {

    public int getAttackPower() {
        return attackPower;
    }

    private final int attackPower;

    public Predator(int speed, int hp, int maxHp, int attackPower, Point position) {

        super(speed, hp, maxHp, position);
        this.attackPower = attackPower;

    }

    @Override
    boolean isTarget(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    Class<? extends Entity> getTargetClass() {
        return Herbivore.class;
    }

    @Override
    void interactWithTarget(Entity target, GameMap map, Point position) {
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

package model;

import java.awt.*;

public class Herbivore extends Creature {

    public Herbivore(int speed, int hp, int maxHp, Point position) {
        super(speed, hp, maxHp, position);
    }

    public void ateGrass() {

    }

    @Override
    boolean isTarget(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    void interactWithTarget(Entity target, GameMap map, Point position) {
        map.removeEntity(position);
        setHp(getHp() + 2);
    }

    @Override
    protected void onNoPath(GameMap map) {
        setHp(getHp() - 1);
    }

    @Override
    public void makeMove(GameMap map) {
        move(map);
    }

    public void takeDamage(int attackPower) {
        setHp(getHp() - attackPower);
    }
}

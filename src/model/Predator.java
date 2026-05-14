package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Predator extends Creature {
    protected int attackPower;

    public Predator(int speed, int hp, int maxHp, int attackPower, Point position) {
        super(speed, hp, maxHp, position);
        this.attackPower = attackPower;

    }

    @Override
    public void makeMove(GameMap map) {
        Point currentPos = this.position;

        if (map.getEntityAt(currentPos) instanceof Herbivore) {
            Herbivore target = (Herbivore) map.getEntityAt(currentPos);
            target.takeDamage(this.attackPower);
            if (target.hp <= 0) {
                map.removeEntity(currentPos);
            }
            return;
        }

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            Point neighbor = new Point(currentPos.x + dx[i], currentPos.y + dy[i]);
            if (map.getEntityAt(neighbor) instanceof Herbivore) {
                Herbivore target = (Herbivore) map.getEntityAt(neighbor);
                target.takeDamage(this.attackPower);
                if (target.hp <= 0) {
                    map.removeEntity(neighbor);
                }
                return;
            }
        }


        PathFinder pathFinder = new PathFinder();
        Point nextStep = pathFinder.findNextStep(map, currentPos, Herbivore.class);

        // 4. Если следующий шаг ведёт на травоядного — атаковать (без перемещения)
        if (nextStep != null && map.getEntityAt(nextStep) instanceof Herbivore) {
            Herbivore target = (Herbivore) map.getEntityAt(nextStep);
            target.takeDamage(this.attackPower);
            if (target.hp <= 0) {
                map.removeEntity(nextStep);
            }
            return;
        }

        if (nextStep != null) {
            map.removeEntity(currentPos);
            position = nextStep;
            map.addEntity(position, this);
        }
    }
}

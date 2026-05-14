package model;

import pathfinder.PathFinder;

import java.awt.*;

public class Herbivore extends Creature {
    public Herbivore(int speed, int hp, int maxHp, Point position) {
        super(speed, hp, maxHp, position);
    }

    public void ateGrass() {

    }

    @Override
    public void makeMove(GameMap map) {
        Point currentPos = this.position;
        if (map.getEntityAt(currentPos) instanceof Grass) {
            map.removeEntity(currentPos);
            this.hp = Math.min(this.hp + 2, maxHp);
            return;
        }
        int[] x = {-1, 1, 0, 0};
        int[] y = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = currentPos.x + x[i];
            int ny = currentPos.y + y[i];
            Point neighbor = new Point(nx, ny);
            if (map.getEntityAt(neighbor) instanceof Grass) {
                map.removeEntity(neighbor);
                map.removeEntity(currentPos);
                position = neighbor;
                map.addEntity(position, this);
                this.hp = Math.min(this.hp + 2, maxHp);
                return;
            }
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

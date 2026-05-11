package simulation;

import actions.Action;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    GameMap map;
    int turnCounter;
    List<Action> initActions;
    List<Action> turnActions;

    public Simulation(int width, int height) {
        map = new GameMap(width, height);
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
    }

    public void startSimulation() {
        for (Action action : initActions) {
            action.execute(map);
        }
        while (true) {
            nextTurn();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(map);
        }
        turnCounter++;
        System.out.println("Turn" + turnCounter);
        render();
    }

    public void addInitAction(Action action) {
        initActions.add(action);
    }

    public void addTurnAction(Action action) {
        turnActions.add(action);
    }

    public void render() {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Point point = new Point(x, y);
                Entity entity = map.getEntityAt(point);
                if (entity == null) {
                    System.out.print(". ");
                } else if (entity instanceof Grass) {
                    System.out.print("G ");
                } else if (entity instanceof Herbivore) {
                    System.out.print("H " );
                } else if (entity instanceof Predator) {
                    System.out.print("P ");
                } else if (entity instanceof Rock) {
                    System.out.print("R ");
                } else if (entity instanceof Tree) {
                    System.out.print("T ");
                }
            }
            System.out.println();
        }
    }

}

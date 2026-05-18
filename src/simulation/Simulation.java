package simulation;

import actions.Action;
import model.*;

import javax.swing.*;
import java.awt.*;
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
            if (map.getAllHerbivores().isEmpty()) {
                System.out.println("No herbivorse left. Stop simulation");
                break;
            }
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
                    System.out.print("\uD83D\uDFEB ");
                } else if (entity instanceof Grass) {
                    System.out.print("\uD83C\uDF3F ");
                } else if (entity instanceof Herbivore) {
                    System.out.print("\uD83D\uDC11 ");
                } else if (entity instanceof Predator) {
                    System.out.print("\uD83D\uDC3A ");
                } else if (entity instanceof Rock) {
                    System.out.print("\uD83E\uDEA8 ");
                } else if (entity instanceof Tree) {
                    System.out.print("\uD83C\uDF32 ");
                }
            }
            System.out.println();
        }
        System.out.println("Herbivores count: "+map.getAllHerbivores().size());
    }

}

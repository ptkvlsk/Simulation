package simulation;

import actions.Action;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Simulation {
    private static final int TURN_DELAY_MS = 500;
    private static final String STOP_MESSAGE = "No herbivores left. Stop simulation";

    private final GameMap map;
    private int turnCounter;
    private final List<Action> initActions;
    private final List<Action> turnActions;

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
            if (map.getEntitiesBy(Herbivore.class).isEmpty()) {
                System.out.println(STOP_MESSAGE);
                break;
            }
            try {
                Thread.sleep(TURN_DELAY_MS);
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
                Optional<Entity> optEntity = map.getEntityAt(point);
                if (optEntity.isEmpty()) {
                    System.out.print("\uD83D\uDFEB ");
                    continue;
                }
                Entity entity = optEntity.get();
                if (entity instanceof Grass) {
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
        System.out.println("Herbivores count: " + map.getEntitiesBy(Herbivore.class).size());
    }
}

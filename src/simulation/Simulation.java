package simulation;

import actions.Action;
import model.*;
import render.Render;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final Render render;
    private volatile boolean running;
    private boolean initialized = false;
    private static final int TURN_DELAY_MS = 500;
    private static final String STOP_MESSAGE = "No herbivores left. Stop simulation";

    private final GameMap map;
    private int turnCounter;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(Render render, int width, int height) {
        this.render = render;
        this.map = new GameMap(width, height);
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(map);
        }
        turnCounter++;
        System.out.println("Turn" + turnCounter);
        render.render(map);
    }

    public void startSimulation() {
        if (!initialized) {
            for (Action action : initActions) {
                action.execute(map);
            }
            initialized = true;
        }
        running = true;
        while (running) {
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

    public void pauseSimulation() {
        running = false;
    }

    public void resumeSimulation() {
        running = true;
        while (running && !map.getEntitiesBy(Herbivore.class).isEmpty()) {
            nextTurn();
            try {
                Thread.sleep(TURN_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addInitAction(Action action) {
        initActions.add(action);
    }

    public void addTurnAction(Action action) {
        turnActions.add(action);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}




import actions.*;
import input.ConsoleInputHandler;
import render.ConsoleRender;
import render.Render;
import simulation.Simulation;
import util.PassabilityChecker;
import pathfinder.PathFinder;

public class Main {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        PassabilityChecker checker = new PassabilityChecker();
        PathFinder pathFinder = new PathFinder(checker);
        Render render = new ConsoleRender();
        Simulation simulation = new Simulation(render, WIDTH, HEIGHT);

        simulation.addInitAction(new SpawnRockAction(30, WIDTH, HEIGHT));
        simulation.addInitAction(new SpawnTreesAction(30, WIDTH, HEIGHT));
        simulation.addInitAction(new SpawnGrassAction(150, WIDTH, HEIGHT));
        simulation.addInitAction(new SpawnHerbivoresAction(15, WIDTH, HEIGHT, 1, 12, 20, pathFinder));
        simulation.addInitAction(new SpawnPredatorsAction(6, WIDTH, HEIGHT, 1, 15, 15, 6, pathFinder));

        simulation.addTurnAction(new MoveCreaturesActions());
        simulation.addTurnAction(new DeletedDeathHerbivores());
        simulation.addTurnAction(new ReproduceHerbivoresAction(30, 0.85, pathFinder));
        simulation.addTurnAction(new RegrowGrassAction(180));
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(simulation);
        consoleInputHandler.startListening();
    }
}
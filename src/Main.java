import actions.*;
import simulation.Simulation;


public class Main {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;


    public static void main(String[] args) {
        Simulation simulation = new Simulation(WIDTH, HEIGHT);

        simulation.addInitAction(new SpawnRockAction(30, WIDTH, HEIGHT));
        simulation.addInitAction(new SpawnTreesAction(30, WIDTH, HEIGHT));

        simulation.addInitAction(new SpawnGrassAction(150, WIDTH, HEIGHT));
        simulation.addInitAction(new SpawnHerbivoresAction(15, WIDTH, HEIGHT, 1, 12, 20));
        simulation.addInitAction(new SpawnPredatorsAction(6, WIDTH, HEIGHT, 1, 15, 15, 6));

        simulation.addTurnAction(new MoveHerbivoreAction());
        simulation.addTurnAction(new MovePredatorAction());
        simulation.addTurnAction(new DeletedDeathHerbivores());
        simulation.addTurnAction(new ReproduceHerbivoresAction(30, 0.99));
        simulation.addTurnAction(new RegrowGrassAction(180));

        simulation.startSimulation();
    }
}
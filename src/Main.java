import actions.*;
import simulation.Simulation;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(30, 30);
        simulation.addInitAction(new SpawnGrassAction(120, 30, 30));
        simulation.addInitAction(new SpawnHerbivoresAction(15, 30, 30, 1, 10));
        simulation.addInitAction(new SpawnPredatorsAction(5, 30, 30, 1, 15, 5));

        simulation.addTurnAction(new MoveHerbivoreAction());
        simulation.addTurnAction(new MovePredotorAction());
        simulation.addTurnAction(new CheckHealthAction());
        simulation.startSimulation();

    }
}
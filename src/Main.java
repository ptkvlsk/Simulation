import actions.*;
import simulation.Simulation;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(30, 30);

        simulation.addInitAction(new SpawnRockAction(30, 30, 30));
        simulation.addInitAction(new SpawnTreesAction(30, 30, 30));

        simulation.addInitAction(new SpawnGrassAction(150, 30, 30));
        simulation.addInitAction(new SpawnHerbivoresAction(15, 30, 30, 1, 10, 20));
        simulation.addInitAction(new SpawnPredatorsAction(5, 30, 30, 1, 15, 15, 5));

        simulation.addTurnAction(new MoveHerbivoreAction());
        simulation.addTurnAction(new MovePredotorAction());
        simulation.addTurnAction(new CheckHealthAction());
        simulation.addTurnAction(new RegroweGrassAction(150));

        simulation.startSimulation();
    }
}
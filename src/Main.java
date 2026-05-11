import actions.*;
import simulation.Simulation;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(20,20);
        simulation.addInitAction(new SpawnGrassAction(20,50,50));
        simulation.addInitAction(new SpawnHerbivoresAction(5,20,20,2,10));
        simulation.addInitAction(new SpawnPredatorsAction(3,20,20,2,15,5));

        simulation.addTurnAction(new MoveHerbivoreAction());
        simulation.addTurnAction(new MovePredotorAction());
        simulation.addTurnAction(new CheckHealthAction());
        simulation.startSimulation();

    }
}
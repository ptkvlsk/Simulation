package input;

import simulation.Simulation;

import java.util.Scanner;

public class ConsoleInputHandler {
    private final Simulation simulation;

    public ConsoleInputHandler(Simulation simulation) {
        System.out.println("Commands: s - start, p - pause, r - resume, q - quit");
        this.simulation = simulation;
    }

    public void startListening() {
        Scanner scanner = new Scanner(System.in);
        Thread thread = new Thread(() -> {
            while (true) {
                String command = scanner.nextLine();
                switch (command) {
                    case "s":
                        new Thread(simulation::startSimulation).start();
                        break;
                    case "p":
                        simulation.pauseSimulation();
                        break;
                    case "r":
                        simulation.resumeSimulation();
                        break;
                    case "q":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Unknown command. Use : s,p,r,q");
                }
            }
        });
        thread.start();
    }
}

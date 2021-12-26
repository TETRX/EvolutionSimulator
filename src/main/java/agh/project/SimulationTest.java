package agh.project;

import agh.project.game.Simulation;
import agh.project.game.information.IMapObserver;
import agh.project.game.map.IWorldMap;

import java.util.Scanner;

public class SimulationTest {
    public static void main(String[] args) {
        System.out.println("hi");
        IWorldMap mockWorldMap = new IWorldMap() {
            @Override
            public void subscribe(IMapObserver observer) {
            }

            @Override
            public void unsubscribe(IMapObserver observer) {
            }

            @Override
            public void notifyObservers() {

            }

            @Override
            public void reproducePhase() {
                System.out.println("reproducing");
            }

            @Override
            public void movementPhase() {
                System.out.println("moving");
            }

            @Override
            public void feedPhase() {
                System.out.println("feeding");
            }

            @Override
            public void growPhase() {
                System.out.println("growing");

            }

            @Override
            public void starvePhase() {
                System.out.println("starving");
            }

            @Override
            public void ageUp() {
            }
        };

        Simulation simulation = new Simulation(mockWorldMap);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                simulation.run();
            }
        });
        thread.start();

        Scanner scanner = new Scanner(System.in);
        boolean end = false;
        while (!end){
            String input = scanner.nextLine();
            switch (input){
                case "p":
                    simulation.pause();
                    break;
                case "u":
                    simulation.unpause();
                    break;
                case "q":
                    simulation.quit();
                    break;
                default:
                    System.out.println(input);
                    break;
            }
        }

    }
}

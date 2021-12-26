package agh.project.game;


import agh.project.game.map.IWorldMap;

import java.util.ArrayList;
import java.util.List;

interface SimulationPhase{
    void run();
}

public class Simulation {
    private final IWorldMap map;
    private boolean finished, paused;
    private int speed;
    private List<SimulationPhase> phases;

    public Simulation(IWorldMap map, int speed) {
        this.map = map;
        finished = false;
        paused = false;
        this.speed = speed;
        phases = new ArrayList<>();
        phases.add(new SimulationPhase() {
            @Override
            public void run() {
                map.starvePhase();
            }
        });
        phases.add(new SimulationPhase() {
            @Override
            public void run() {
                map.movementPhase();
            }
        });
        phases.add(new SimulationPhase() {
            @Override
            public void run() {
                map.feedPhase();
            }
        });
        phases.add(new SimulationPhase() {
            @Override
            public void run() {
                map.reproducePhase();
            }
        });
        phases.add(new SimulationPhase() {
            @Override
            public void run() {
                map.growPhase();
            }
        });
    }

    public Simulation(IWorldMap map){
        this(map, 1);
    }

    public void run(){
        System.out.println("running");
        while (!isFinished()) {
            map.ageUp();
            map.notifyObservers();
            for (SimulationPhase phase :
                    phases) {
                waitForUnpause();
                if (isFinished()) {
                    break;
                }
                phase.run();
            }
            delay();
        }
    }

    private static final int BASE_SPEED = 500;

    private synchronized void delay(){
        try {
            Thread.sleep(BASE_SPEED/speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private synchronized boolean isFinished(){
        return finished;
    }

    public synchronized void quit(){
        finished=true;
    }

    private void waitForUnpause(){
        while (paused){
            try{
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }



    public synchronized void pause(){
        paused=true;
    }

    public synchronized void unpause(){
        paused=false;
        this.notify();
    }
}

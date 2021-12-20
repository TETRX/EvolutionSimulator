package agh.project.game.reproduction;

import agh.project.game.map.AnimalLocationMap;

import java.util.List;

public class ReproductionResults {
    private final AnimalLocationMap animals;
    private final List<IReproductionEvent> events;

    public ReproductionResults(AnimalLocationMap animals, List<IReproductionEvent> events) {
        this.animals = animals;
        this.events = events;
    }

    public AnimalLocationMap getAnimals() {
        return animals;
    }

    public List<IReproductionEvent> getEvents() {
        return events;
    }
}

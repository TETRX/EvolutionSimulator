package agh.project.stats.stattrackers;

import agh.project.game.animals.Animal;
import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.stats.SingleStatTracker;

import java.util.HashSet;
import java.util.Set;

public class AverageLifetimeTracker extends SingleStatTracker<Float> {
    private float averageLifetime;
    private int deadNumber;
    private Set<AnimalData> lastAlive;

    public AverageLifetimeTracker() {
        averageLifetime = 0;
        deadNumber = 0;
        lastAlive = new HashSet<>();
    }


    @Override
    public Float calculateUpdate(MapState mapState) {
        Set<AnimalData> newlyDead = new HashSet<>(lastAlive);
        lastAlive.clear();
        for (MapLocation location :
                mapState.animalData.keySet()) {
            for (AnimalData animal :
                    mapState.animalData.get(location)) {
                newlyDead.remove(animal); // a bit hacky, animalData objects are identified by their reference to an Animal
                lastAlive.add(animal);
            }
        }
        int newlyDeadTotalLifespan = 0;
        for (AnimalData animal :
                newlyDead) {
            newlyDeadTotalLifespan+=animal.age;
        }
        if (!newlyDead.isEmpty()) { // if there's at least one new dead animal, update the average
            averageLifetime = ((averageLifetime * deadNumber) + newlyDeadTotalLifespan) / (deadNumber + newlyDead.size());
        }
        return averageLifetime;
    }

    @Override
    public String statName() {
        return "Average lifetime";
    }
}

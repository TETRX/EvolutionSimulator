package agh.project.game.information;

import agh.project.game.map.MapLocation;
import agh.project.game.movement.Bounds;

import java.util.*;

public class MapState {
    public final Map<MapLocation, Set<AnimalData>> animalData;
    public final Set<MapLocation> foodData;
    public final int age;
    public final Phase phase;
    public final Bounds bounds;
    public final List<MapLocation> newGrassLocations;
    public final List<MapLocation> noLongerGrassLocations;

    public MapState(List<AnimalData> animalData, Set<MapLocation> foodData, int age, Phase phase, Bounds bounds, List<MapLocation> newGrassLocations, List<MapLocation> noLongerGrassLocations) {
        this.newGrassLocations = newGrassLocations;
        this.noLongerGrassLocations = noLongerGrassLocations;
        this.animalData = new HashMap<>();
        for (AnimalData animal :
                animalData) {
            MapLocation location = animal.state.getLocation();
            this.animalData.computeIfAbsent(location,k -> new HashSet<>());
            this.animalData.get(location).add(animal);
        }


        this.foodData = foodData;
        this.age = age;
        this.phase = phase;
        this.bounds = bounds;
    }
}

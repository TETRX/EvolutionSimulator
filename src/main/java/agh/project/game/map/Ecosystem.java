package agh.project.game.map;

import agh.project.utils.Sampler;

import java.util.*;
import java.util.stream.Collectors;

public class Ecosystem {
    private final Set<MapLocation> locations;
    private Map<MapLocation, Food> foodMap;
    private Set<MapLocation> noPlantGrowthLocations;
    
    private List<MapLocation> newGrass;
    private List<MapLocation> noLongerGrass;

    private final Sampler<MapLocation> sampler;

    public Ecosystem(Set<MapLocation> locations, Random random) {
        this.locations = locations;
        foodMap = new HashMap<>();
        sampler = new Sampler<MapLocation>(random);
        noPlantGrowthLocations = new HashSet<>(locations);
        newGrass = new ArrayList<>();
        noLongerGrass = new ArrayList<>();
    }

    public void growRandom(Food plant, Set<MapLocation> forbiddenLocations){
        Set<MapLocation> potentialGrowingLocations = new HashSet<>(noPlantGrowthLocations);
        potentialGrowingLocations.removeAll(forbiddenLocations);
        if(potentialGrowingLocations.size()==0){
            return;
        }
        MapLocation growthPlace = sampler.randomElement(new ArrayList<>(potentialGrowingLocations));
        grow(plant,growthPlace);
    }

    private void grow(Food plant, MapLocation growingSite){
        noPlantGrowthLocations.remove(growingSite);
        newGrass.add(growingSite);
        foodMap.put(growingSite,plant);
    }

    public Food foodAt(MapLocation mapLocation){
        return foodMap.get(mapLocation);
    }

    public void deleteFood(MapLocation mapLocation){
        foodMap.remove(mapLocation);
        noLongerGrass.add(mapLocation);
        noPlantGrowthLocations.add(mapLocation);
    }

    public Set<MapLocation> getFoodData(){
        return new HashSet<>(foodMap.keySet());
    }
    
    public List<MapLocation> getNewGrass(){
        List<MapLocation> result = new ArrayList<>(newGrass);
        newGrass = new ArrayList<>();
        return result;
    }

    public List<MapLocation> getNoLongerGrass(){
        List<MapLocation> result = new ArrayList<>(noLongerGrass);
        noLongerGrass = new ArrayList<>();
        return result;
    }

}

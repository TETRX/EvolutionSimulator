package agh.project.game.map;

import agh.project.game.animals.Animal;
import agh.project.utils.UpperBound;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalLocationMap extends HashMap<MapLocation, ArrayList<Animal>> { // Technically doesn't fit Liskov's substitution principle, but extends is useful here to avoid code repetition

    @Override
    public ArrayList<Animal> get(Object o) {
        computeIfAbsent((MapLocation) o, k -> new ArrayList<>());
        return super.get(o);
    }

    public void removeFrom(MapLocation mapLocation, Animal animal) {
        super.get(mapLocation).remove(animal);
        if(super.get(mapLocation).isEmpty()){
            super.remove(mapLocation);
        }
    }

    public void addTo(MapLocation mapLocation, Animal animal){
        get(mapLocation).add(animal);

    }

    public List<Animal> getNFittest(MapLocation mapLocation, int n){
        List<Animal> sortedAnimals = get(mapLocation).stream().sorted().collect(Collectors.toList());
//        System.out.println(sortedAnimals);
        int upperBound = UpperBound.binarySearch(sortedAnimals, sortedAnimals.get(n-1));
//        System.out.println(sortedAnimals.get(n-1));
//        System.out.println(upperBound);
        return sortedAnimals.stream().limit(upperBound).collect(Collectors.toList());
    }

    public int animalCount(){
        int sum = 0;
        for (MapLocation mapLocation: keySet()) {
            sum+=animalCount(mapLocation);
        }
        return sum;
    }

    public int animalCount(MapLocation mapLocation){
        return get(mapLocation).size();
    }

    public void add(AnimalLocationMap animalLocationMap){
        for (MapLocation mapLocation: animalLocationMap.keySet()) {
            computeIfAbsent(mapLocation, k -> new ArrayList<>());
            get(mapLocation).addAll(animalLocationMap.get(mapLocation));
        }
    }
}

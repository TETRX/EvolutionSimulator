package agh.project.game.map;

import agh.project.game.animals.Animal;
import agh.project.game.animals.AnimalMove;
import agh.project.game.animals.Genom;
import agh.project.game.animals.IAnimalMap;
import agh.project.game.information.*;
import agh.project.game.movement.Bounds;
import agh.project.game.movement.IMovementRules;
import agh.project.game.reproduction.IReproductionRules;
import agh.project.game.reproduction.ReproductionResults;

import java.util.*;

public class WorldMap implements IAnimalMap, IWorldMap, IObservableMap {

    public WorldMap(float startingEnergy, float daysWorthOfEnergy, IMovementRules movementRules, IReproductionRules reproductionRules,
                    int width, int height, float jungleRatio, Random random, Map<Animal, AnimalState> startingAnimalStates, float plantEnergy) {
        this.startingEnergy = startingEnergy;
        this.daysWorthOfEnergy = daysWorthOfEnergy;
        this.movementRules = movementRules;
        this.reproductionRules = reproductionRules;
        this.random = random;
        this.animalStates = new HashMap<>(startingAnimalStates);
        this.plantEnergy = plantEnergy;
        this.animalLocationMap = new AnimalLocationMap();
        for (Animal animal :
                animalStates.keySet()) {
            animalLocationMap.addTo(animalStates.get(animal).getLocation(), animal);
        }

        mapBounds = new Bounds(0,height,0,width);
        int jungleHeight=Math.round(height*jungleRatio), jungleWidth= Math.round(width*jungleRatio);
        jungleBounds = new Bounds(height/2-jungleHeight/2,
                height/2+(jungleHeight+1)/2,
                width/2-jungleWidth/2,
                width/2+(jungleWidth+1)/2);

        Set<MapLocation> savannaLocations = new HashSet<>(mapBounds.getAllWithinBounds());
        Ecosystem savanna = new Ecosystem(savannaLocations, random);
        Set<MapLocation> jungleLocations = jungleBounds.getAllWithinBounds();
        savannaLocations.removeAll(jungleLocations);
        Ecosystem jungle = new Ecosystem(jungleLocations,random);

        ecosystems = new ArrayList<>();
        ecosystems.add(jungle);
        ecosystems.add(savanna);

        ecosystemMap = new HashMap<>();
        for (MapLocation savannaLocation :
                savannaLocations) {
            ecosystemMap.put(savannaLocation, savanna);
        }
        for (MapLocation jungleLocation :
                jungleLocations) {
            ecosystemMap.put(jungleLocation, jungle);
        }
        observers = new HashSet<>();
    }

    private Map<Animal,AnimalState> animalStates;
    private AnimalLocationMap animalLocationMap;

    private final float startingEnergy;
    private final float daysWorthOfEnergy;
    private final float plantEnergy;


    private final IMovementRules movementRules;
    private final IReproductionRules reproductionRules;

    private final Bounds mapBounds, jungleBounds;

    private final Map<MapLocation, Ecosystem> ecosystemMap;
    private final List<Ecosystem> ecosystems;

    private final Random random;

    private int age;

    private final Set<IMapObserver> observers;


    @Override
    public void notifyOfMove(Animal animal, AnimalMove move) {
        AnimalState oldState = animalStates.get(animal);
        AnimalState newState = movementRules.move(oldState, move);
        animalStates.put(animal,newState);
        if(!oldState.getLocation().equals( newState.getLocation())){
            animalLocationMap.get(oldState.getLocation()).remove(animal);
            animalLocationMap.get(newState.getLocation()).add(animal);
        }
    }

    @Override
    public float getStartingEnergy() {
        return startingEnergy;
    }

    @Override
    public void reproducePhase() {
        ReproductionResults results = reproductionRules.reproduce(animalLocationMap);

        AnimalLocationMap children = results.getAnimals();
        for (MapLocation location :
                children.keySet()) {
            for (Animal animal :
                    children.get(location)) {
                animalStates.put(animal, new AnimalState(MapOrientation.getRandom(random),location));
            }
        }
        animalLocationMap.add(children);

    }

    @Override
    public void movementPhase() {
        Map<Animal,AnimalState> newStates = new HashMap<>();

        for (Animal animal :
                animalStates.keySet()) {
            AnimalMove move = animal.move();
            AnimalState oldAnimalState = animalStates.get(animal);
            AnimalState newAnimalState = movementRules.move(oldAnimalState, move);

            MapLocation newLocation = newAnimalState.getLocation();
            MapLocation oldLocation = oldAnimalState.getLocation();
            if(!oldLocation.equals(newLocation)){
                animalLocationMap.removeFrom(oldLocation,animal);
                animalLocationMap.addTo(newLocation,animal);
            }
            newStates.put(animal,newAnimalState);
        }
        animalStates = newStates;
    }

    private Food foodAt(MapLocation mapLocation){
        return ecosystemMap.get(mapLocation).foodAt(mapLocation);
    }

    private void feedLocally(MapLocation mapLocation){
        Food food = foodAt(mapLocation);
        if (food!=null){
            if (animalLocationMap.animalCount(mapLocation)>0){
                List<Animal> eatingAnimals = animalLocationMap.getNFittest(mapLocation,1);
                float energyDivided = food.getEnergyProvided()/eatingAnimals.size();
                for (Animal animal :
                        eatingAnimals) {
                    animal.feed(energyDivided);
                }
                ecosystemMap.get(mapLocation).deleteFood(mapLocation);
            }
        }
    }

    @Override
    public void feedPhase() {
        for (MapLocation mapLocation :
                animalLocationMap.keySet()) {
                feedLocally(mapLocation);
        }
    }

    private Set<MapLocation> animalLocations(){
        Set<MapLocation> mapLocations = new HashSet<>();
        for (AnimalState animalState :
                animalStates.values()) {
            mapLocations.add(animalState.getLocation());
        }
        return mapLocations;
    }

    @Override
    public void growPhase() {
        for (Ecosystem ecosystem :
                ecosystems) {
            Food plant = new Food(daysWorthOfEnergy);
            ecosystem.growRandom(plant,animalLocations());
        }
    }

    @Override
    public void starvePhase() {
        List<Animal> starved = new ArrayList<>();
        for (Animal animal :
                animalStates.keySet()) {
            animal.age(daysWorthOfEnergy);
            if (animal.getEnergyLevel()<=0){
                starved.add(animal);
            }
        }

        for (Animal animal :
                starved) {
            MapLocation corpseLocation = animalStates.get(animal).getLocation();
            animalStates.remove(animal);
            animalLocationMap.removeFrom(corpseLocation,animal);
        }
    }

    @Override
    public void ageUp() {
        age++;
    }

    public void notifyObservers(){
        notifyObservers(currentMapState());
    }

    private synchronized void notifyObservers(MapState mapState){
        for (IMapObserver observer :
                observers) {
            observer.notify(mapState);
        }
    }

    private AnimalData getAnimalData(Animal animal){
        return new AnimalData(animal,
                animal.getEnergyLevel(),
                animal.getGenom(),
                animal.getAge(),
                animalStates.get(animal),
                animal.getChildren().size());
    }

    private MapState currentMapState(){
        Set<MapLocation> foodData = new HashSet<>();
        List<MapLocation> newGrassLocations = new ArrayList<>();
        List<MapLocation> noLongerGrassLocations = new ArrayList<>();
        for (Ecosystem e :
                ecosystems) {
            foodData.addAll(e.getFoodData());
            newGrassLocations.addAll(e.getNewGrass());
            noLongerGrassLocations.addAll(e.getNoLongerGrass());
        }

        List<AnimalData> animalData = new ArrayList<>();
        for (Animal animal :
                animalStates.keySet()) {
            animalData.add(getAnimalData(animal));
        }
        return new MapState(animalData,foodData,age, mapBounds, newGrassLocations, noLongerGrassLocations);
    }

    @Override
    public synchronized void subscribe(IMapObserver observer) {
        observers.add(observer);
    }

    @Override
    public synchronized void unsubscribe(IMapObserver observer) {
        observers.remove(observer);
    }
}

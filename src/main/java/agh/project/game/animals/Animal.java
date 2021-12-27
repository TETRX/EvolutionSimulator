package agh.project.game.animals;

import java.util.*;

public class Animal implements Comparable<Animal>{
    public Animal(float energyLevel, IAnimalMap map, Genom genom, Random random, List<Animal> childParents) {
        this(energyLevel,genom,random,childParents);
        this.map=map;
    }

    public float getEnergyLevel() {
        return energyLevel;
    }

    public Animal(float energyLevel, Genom genom, Random random, List<Animal> parents) {
        this.parents = parents;
        this.energyLevel = energyLevel;
        this.genom = genom;
        this.random = random;
        children = new ArrayList<>();
        descendants = new HashSet<>();
    }

    public Animal(float energyLevel, Genom genom, Random random){
        this(energyLevel,genom,random,new ArrayList<>());
    }

    private final List<Animal> parents;

    public List<Animal> getChildren() {
        return children;
    }

    private final List<Animal> children;

    private void addDescendant(Animal newborn){
        if(!descendants.contains(newborn)) {
            descendants.add(newborn);
            for (Animal parent :
                    parents) {
                parent.addDescendant(newborn);
            }
        }
    }

    public Set<Animal> getDescendants() {
        return descendants;
    }

    private final Set<Animal> descendants;
    
    private float energyLevel;

    public int getAge() {
        return age;
    }

    private int age;

    public void setMap(IAnimalMap map) {
        this.map = map;
    }

    private IAnimalMap map;

    public Genom getGenom() {
        return genom;
    }

    private final Genom genom;

    private final Random random;

    public AnimalMove move() {

        return genom.move();
    }

    public void age(float foodNeeded){
        energyLevel-=foodNeeded;
        age++;
    }

    public void feed(float food) {
        energyLevel += food;
    }

    @Override
    public int compareTo(Animal animal) {
        return (int) (this.energyLevel-animal.energyLevel);
    }

    public Animal reproduce(Animal partner){
        float energyShare = energyLevel/(energyLevel+partner.energyLevel);

        Genom childGenom = new Genom(this.genom,partner.genom,energyShare,random);

        float energySpent = energyLevel/4;
        float partnerEnergySpent = partner.energyLevel/4;
        energyLevel-=energySpent;
        partner.energyLevel-=partnerEnergySpent;
        float childEnergy = energySpent+partnerEnergySpent;

        List<Animal> childParents = new ArrayList<Animal>();
        childParents.add(this);
        childParents.add(partner);

        Animal child = new Animal(childEnergy,map,childGenom,random,childParents);
        children.add(child);
        addDescendant(child);

        partner.children.add(child);
        partner.addDescendant(child);

        return child;
    }

    public Animal reproduceAsexually(){
        List<Animal> childParents = new ArrayList<Animal>();
        childParents.add(this);
        return new Animal(map.getStartingEnergy(), map, genom, random, childParents);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "energyLevel=" + energyLevel +
                '}';
    }
}

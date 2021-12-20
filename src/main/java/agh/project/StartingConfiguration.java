package agh.project;

import agh.project.game.movement.IMovementRules;
import agh.project.game.reproduction.IReproductionRules;

public class StartingConfiguration{
    public static final int DEFAULT_WIDTH = 50, DEFAULT_HEIGHT=50, DEFAULT_ANIMAL_N=20;
    public static final float DEFAULT_START_ENERGY = 20.0f, DEFAULT_MOVE_ENERGY = 0.5f, DEFAULT_PLANT_ENERGY = 5.0f, DEFAULT_JUNGLE_RATIO = 0.1f;

    public static final float MAX_START_ENERGY = 100.0f, MAX_MOVE_ENERGY = 10f, MAX_PLANT_ENERGY = 10f;

    public final int width, height, startingAnimalNumber;
    public final float startEnergy, moveEnergy, plantEnergy, jungleRatio;
    public final IReproductionRules reproductionRules;
    public final IMovementRules movementRules;

    StartingConfiguration(int width, int height, int startingAnimalNumber, float startEnergy, float moveEnergy, float plantEnergy, float jungleRatio, IReproductionRules reproductionRules, IMovementRules movementRules) {
        this.width = width;
        this.height = height;
        this.startingAnimalNumber = startingAnimalNumber;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.reproductionRules = reproductionRules;
        this.movementRules = movementRules;
    }

    @Override
    public String toString() {
        return "StartingConfiguration{" +
                "width=" + width +
                ", height=" + height +
                ", startingAnimalNumber=" + startingAnimalNumber +
                ", startEnergy=" + startEnergy +
                ", moveEnergy=" + moveEnergy +
                ", plantEnergy=" + plantEnergy +
                ", jungleRatio=" + jungleRatio +
                ", reproductionRules=" + reproductionRules +
                ", movementRules=" + movementRules +
                '}';
    }
}
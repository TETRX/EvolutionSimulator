package agh.project.game.movement;

import agh.project.game.animals.AnimalMove;
import agh.project.game.map.AnimalState;
import agh.project.game.map.MapLocation;
import agh.project.game.map.MapOrientation;

public abstract class StandardMovementRules implements IMovementRules{

    public AnimalState move(AnimalState animalState, AnimalMove move) {
        return move.move(animalState);
    }
}

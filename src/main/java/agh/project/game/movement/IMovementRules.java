package agh.project.game.movement;

import agh.project.game.animals.AnimalMove;
import agh.project.game.map.AnimalState;

public interface IMovementRules {
    AnimalState move(AnimalState animalState, AnimalMove move);
}

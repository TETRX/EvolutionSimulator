package agh.project.game.animals;

import agh.project.game.movement.Spin;

public interface IAnimalMap {
    void notifyOfMove(Animal animal, AnimalMove move);
    float getStartingEnergy();
}

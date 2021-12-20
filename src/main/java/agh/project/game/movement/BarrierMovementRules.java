package agh.project.game.movement;

import agh.project.game.animals.AnimalMove;
import agh.project.game.map.AnimalState;

public class BarrierMovementRules extends StandardMovementRules{
    private final Bounds bounds;

    public BarrierMovementRules(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public AnimalState move(AnimalState animalState, AnimalMove move) {
        AnimalState unboundedAnimalState = super.move(animalState,move);
        if (bounds.outOfBounds(unboundedAnimalState.getLocation())){
            return animalState;
        }
        return unboundedAnimalState;
    }
}

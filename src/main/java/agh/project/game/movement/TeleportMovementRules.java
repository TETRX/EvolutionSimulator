package agh.project.game.movement;

import agh.project.game.animals.AnimalMove;
import agh.project.game.map.AnimalState;
import agh.project.game.map.MapLocation;

public class TeleportMovementRules extends StandardMovementRules{
    private final Bounds bounds;

    public TeleportMovementRules(Bounds bounds) {
        this.bounds = bounds;
    }

    private int teleportIntoBounds1D(int value, int l, int r){
        return (value+r-l)%(r-l)+l;
    }

    @Override
    public AnimalState move(AnimalState animalState, AnimalMove animalMove) {
        AnimalState unBoundedAnimalState=super.move(animalState,animalMove);
        MapLocation unBoundedMapLocation= unBoundedAnimalState.getLocation();
        MapLocation boundedLocation = new MapLocation(teleportIntoBounds1D(unBoundedMapLocation.x,bounds.getLeft(),bounds.getRight()),
                teleportIntoBounds1D(unBoundedMapLocation.y,bounds.getDown(),bounds.getUp()));
        return new AnimalState(unBoundedAnimalState.getOrientation(),boundedLocation);
    }
}

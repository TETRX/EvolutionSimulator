package agh.project.game.animals;

import agh.project.game.map.AnimalState;
import agh.project.game.map.MapLocation;
import agh.project.game.map.MapOrientation;
import agh.project.game.movement.Spin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum AnimalMove {
    FORWARD(new Spin(0),1),
    FORWARD_LEFT(new Spin(1),0),
    LEFT(new Spin(2),0),
    BACKWARD_LEFT(new Spin(3),0),
    BACKWARD(new Spin(0),-1),
    BACKWARD_RIGHT(new Spin(5),0),
    RIGHT(new Spin(6),0),
    FORWARD_RIGHT(new Spin(7),0),
    ;
    public final Spin spin;
    public final int moveSpeed;

    public static final List<AnimalMove> VALUES =
            List.of(values());
    AnimalMove(Spin spin, int moveSpeed) {
        this.spin = spin;
        this.moveSpeed = moveSpeed;
    }

    public AnimalState move(AnimalState from){
        MapOrientation oldOrientation = from.getOrientation();
        MapOrientation newOrientation = oldOrientation.applySpin(spin);
        MapLocation newLocation = from.getLocation().applyMove(oldOrientation.moveInDirection().scale(moveSpeed));
        return new AnimalState(newOrientation,newLocation);
    }
}

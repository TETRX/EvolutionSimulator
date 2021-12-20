package agh.project.game.map;

import agh.project.game.movement.Move;
import agh.project.game.movement.Spin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MapOrientation {
    public final int orientation;

    public static final Map<MapOrientation, Move> MOVE_IN_DIRECTION = new HashMap<>();

    static {
        MOVE_IN_DIRECTION.put(new MapOrientation(0), new Move(0,1));
        MOVE_IN_DIRECTION.put(new MapOrientation(1), new Move(1,1));
        MOVE_IN_DIRECTION.put(new MapOrientation(2), new Move(1,0));
        MOVE_IN_DIRECTION.put(new MapOrientation(3), new Move(1,-1));
        MOVE_IN_DIRECTION.put(new MapOrientation(4), new Move(0,-1));
        MOVE_IN_DIRECTION.put(new MapOrientation(5), new Move(-1,-1));
        MOVE_IN_DIRECTION.put(new MapOrientation(6), new Move(-1,0));
        MOVE_IN_DIRECTION.put(new MapOrientation(7), new Move(-1,1));
    }

    public static MapOrientation getRandom(Random random){
        return new MapOrientation(random.nextInt(8));
    }

    public Move moveInDirection(){
        return MOVE_IN_DIRECTION.get(this);
    }

    public MapOrientation(int orientation) {
        this.orientation = orientation;
    }

    public MapOrientation applySpin(Spin spin){
        return new MapOrientation((orientation+ spin.spinNumber)%8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOrientation that = (MapOrientation) o;
        return orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation);
    }

    @Override
    public String toString() {
        return "MapOrientation{" +
                "orientation=" + orientation +
                '}';
    }
}

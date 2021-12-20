package agh.project.game.map;

public class AnimalState {
    public MapOrientation getOrientation() {
        return orientation;
    }

    public MapLocation getLocation() {
        return location;
    }

    private MapOrientation orientation;
    private MapLocation location;

    public AnimalState(MapOrientation orientation, MapLocation location) {
        this.orientation = orientation;
        this.location = location;
    }

}

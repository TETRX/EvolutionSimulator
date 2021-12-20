package agh.project.game.map;

import agh.project.game.movement.Move;

import java.util.Objects;

public class MapLocation {
    public final int x,y;

    public MapLocation(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapLocation that = (MapLocation) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public MapLocation applyMove(Move move){
        return new MapLocation(x+ move.x, y+ move.y);
    }

    @Override
    public String toString() {
        return "MapLocation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

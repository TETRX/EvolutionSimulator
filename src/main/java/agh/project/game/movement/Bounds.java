package agh.project.game.movement;

import agh.project.game.map.MapLocation;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Bounds {
    private final int down,up,left,right;

    public Bounds(int down, int up, int left, int right) {
        this.down = down;
        this.up = up;
        this.left = left;
        this.right = right;
    }

    public int getDown() {
        return down;
    }

    public int getUp() {
        return up;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public boolean outOfBounds(MapLocation mapLocation){
        return mapLocation.x<left || mapLocation.x>=right || mapLocation.y<down || mapLocation.y>=up;
    }

    public Set<MapLocation> getAllWithinBounds(){
        Set<MapLocation> all = new HashSet<>();
        for(int x=left;x<right;x++){
            for (int y=down;y<up;y++){
                all.add(new MapLocation(x,y));
            }
        }
        return all;
    }

    public MapLocation getRandomWithinBounds(Random random){
        return new MapLocation(random.nextInt(up+down)-down, random.nextInt(left+right)-left );
    }

}

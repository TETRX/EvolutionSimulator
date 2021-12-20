package agh.project.game.movement;

public class Move {
    public final int x, y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move scale(int scalar){
        return new Move(scalar*x,scalar*y);
    }
}

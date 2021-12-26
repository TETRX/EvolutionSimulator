package agh.project.game.movement;

public class Spin {
    public final int spinNumber;

    public Spin(int spinNumber) {
        this.spinNumber = spinNumber;
    }


    @Override
    public String toString() {
        return String.valueOf(spinNumber);
    }
}

package agh.project.game.animals;

import agh.project.game.movement.Spin;

import java.util.Random;

public class Gene {
    AnimalMove prefferedMove;
    public Gene(int move){
        prefferedMove = AnimalMove.VALUES.get(move);
    }

    public static Gene getRandom(Random random){
        return new Gene(random.nextInt(7));
    }
}

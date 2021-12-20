package agh.project.game.animals;

import agh.project.utils.Sampler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genom {
    private final Gene[] genes;
    private final Random random;
    public static final int GENE_NUMBER=32;

    public Genom(Random random) {
        this.random = random;
        genes = new Gene[GENE_NUMBER];
    }

    public Genom(Genom singleParent, Random random){
        this(random);
        for (int i = 0; i < GENE_NUMBER; i++) {
            genes[i]=singleParent.genes[i];
        }
    }

    public void randomizeGenom(){
        for (int i = 0; i < GENE_NUMBER; i++) {
            genes[i]=Gene.getRandom(random);
        }
    }

    public Genom(Genom parent1, Genom parent2, float parent1Share, Random random){
        this(random);
        float floatShare = GENE_NUMBER*parent1Share;
        int roundDownShare = Math.round(floatShare);
        float floatPart = floatShare-roundDownShare;
        int intShare = roundDownShare+(random.nextFloat()<floatPart ? 1 : 0); //randomly round up or down so that the expected value equals the accurate real share

        List<Integer> geneIndices = new ArrayList<>(GENE_NUMBER);
        for(int i=0;i<GENE_NUMBER;i++){
            geneIndices.add(i);
        }
        List<Integer> myIndices = new Sampler<Integer>(random).randomSample(geneIndices, intShare);

        for (Integer i :
                myIndices) {
            genes[i]=parent1.genes[i];
        }
        for (int i = 0;i<GENE_NUMBER;i++){
            if(genes[i]==null){
                genes[i]=parent2.genes[i];
            }
        }
    }

    public AnimalMove move(){
        AnimalMove move = genes[random.nextInt(genes.length)].prefferedMove;
        return move;
    }
}

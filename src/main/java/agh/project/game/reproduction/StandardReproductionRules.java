package agh.project.game.reproduction;

import agh.project.game.animals.Animal;
import agh.project.game.map.AnimalLocationMap;
import agh.project.game.map.MapLocation;
import agh.project.utils.Sampler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StandardReproductionRules implements IReproductionRules {
    protected final Random random;
    protected final float minimalReproductionEnergy;

    public StandardReproductionRules(Random random, float minimalReproductionEnergy) {
        this.random = random;
        this.minimalReproductionEnergy = minimalReproductionEnergy;
    }

    @Override
    public ReproductionResults reproduce(AnimalLocationMap animalsMap) {
        AnimalLocationMap childLocationMap = new AnimalLocationMap();
        for (MapLocation mapLocation:
                animalsMap.keySet()) {
            if (animalsMap.animalCount(mapLocation)>=2){
                List<Animal> candidatesToReproduce = animalsMap.getNFittest(mapLocation, 2);
                if (candidatesToReproduce.get(1).getEnergyLevel()<minimalReproductionEnergy){
                    continue;
                }

                Animal[] animalsReproducing = new Animal[2];
                if(candidatesToReproduce.size()>2){
                    if(candidatesToReproduce.get(0).getEnergyLevel()==candidatesToReproduce.get(1).getEnergyLevel()){ // draw between all candidates
                        animalsReproducing = new Sampler<Animal>(random).randomSample(candidatesToReproduce, 2).toArray(new Animal[0]);
                    }
                    else { //second place is drawn
                        animalsReproducing[0]=candidatesToReproduce.get(0);
                        animalsReproducing[1]=candidatesToReproduce.get(random.nextInt(candidatesToReproduce.size()-1)+1);
                    }
                }
                else {
                    animalsReproducing[0]=candidatesToReproduce.get(0);
                    animalsReproducing[1]=candidatesToReproduce.get(1);
                }
                childLocationMap.addTo(mapLocation, animalsReproducing[0].reproduce(animalsReproducing[1]));
            }
        }
        return new ReproductionResults(childLocationMap, new ArrayList<>());
    }


}

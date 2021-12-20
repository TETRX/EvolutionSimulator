package agh.project.game.reproduction;

import agh.project.game.animals.Animal;
import agh.project.game.map.AnimalLocationMap;
import agh.project.game.map.MapLocation;
import agh.project.game.movement.Bounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagicalReproductionRules extends StandardReproductionRules {
    public static final int MAGIC_NUMBER = 5;
    private int mana;
    private int startingMana;
    private Bounds bounds;

    public MagicalReproductionRules(Random random, float minimalReproductionEnergy, int startingMana,Bounds bounds) {
        super(random, minimalReproductionEnergy);
        this.startingMana=startingMana;
        this.mana=startingMana;
        this.bounds=bounds;
    }

    @Override
    public ReproductionResults reproduce(AnimalLocationMap animalsMap){
        AnimalLocationMap magicalChildren = new AnimalLocationMap();
        List<IReproductionEvent> magicalEvents = new ArrayList<>();
        if (animalsMap.animalCount()==MAGIC_NUMBER && mana>0){
            mana--;
            for (MapLocation mapLocation :
                    animalsMap.keySet()) {
                for (Animal animal: animalsMap.get(mapLocation)) {
                    Animal child = animal.reproduceAsexually();
                    magicalChildren.addTo(new MapLocation(random.nextInt(bounds.getRight()- bounds.getLeft())+ bounds.getRight(),
                            random.nextInt(bounds.getUp()- bounds.getDown())+bounds.getDown()),child);
                }
            }
            magicalEvents.add(new MagicalReproductionEvent(startingMana-mana));
            return new ReproductionResults(magicalChildren,magicalEvents);
        }
        else {
            return super.reproduce(animalsMap);
        }
    }

}

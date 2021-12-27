package agh.project.ui.animalinfo.animalinfogetters;

import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;

public class ChildrenNumberGetter implements IAnimalInfoGetter{
    private final AnimalData startingAnimalData;
    public ChildrenNumberGetter(AnimalData startingAnimalData){
        this.startingAnimalData = startingAnimalData;
    }

    @Override
    public String getName() {
        return "Children number";
    }

    @Override
    public String getValue(AnimalData animalData, MapState context) {
        if(animalData!=null) {
            return String.valueOf(animalData.childrenNumber - startingAnimalData.childrenNumber);
        }
        return null;
    }
}

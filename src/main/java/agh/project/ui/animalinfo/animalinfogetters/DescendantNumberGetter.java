package agh.project.ui.animalinfo.animalinfogetters;

import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;

public class DescendantNumberGetter implements IAnimalInfoGetter{
    private final AnimalData startingAnimalData;
    public DescendantNumberGetter(AnimalData startingAnimalData){
        this.startingAnimalData = startingAnimalData;
    }

    @Override
    public String getName() {
        return "Descendant number";
    }

    @Override
    public String getValue(AnimalData animalData, MapState context) {
        if(animalData!=null) {
            return String.valueOf(animalData.descendantNumber - startingAnimalData.descendantNumber);
        }
        return null;
    }
}

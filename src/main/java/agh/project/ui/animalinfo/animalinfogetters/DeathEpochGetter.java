package agh.project.ui.animalinfo.animalinfogetters;

import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;

public class DeathEpochGetter implements IAnimalInfoGetter{
    @Override
    public String getName() {
        return "Death epoch";
    }

    @Override
    public String getValue(AnimalData animalData, MapState context) {
        if (animalData==null){
            return String.valueOf(context.age);
        }
        return "-";
    }
}

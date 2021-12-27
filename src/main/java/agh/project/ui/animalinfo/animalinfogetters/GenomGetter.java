package agh.project.ui.animalinfo.animalinfogetters;

import agh.project.game.animals.Genom;
import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;

public class GenomGetter implements IAnimalInfoGetter {
    @Override
    public String getName() {
        return "Genom";
    }

    @Override
    public String getValue(AnimalData animalData, MapState context) {
        if (animalData!=null) {
            return animalData.genom.toString();
        }
        return null;
    }
}

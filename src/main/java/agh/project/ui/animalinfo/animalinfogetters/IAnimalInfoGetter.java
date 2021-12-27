package agh.project.ui.animalinfo.animalinfogetters;

import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;

public interface IAnimalInfoGetter {
    String getName();
    String getValue(AnimalData animalData, MapState context);
}

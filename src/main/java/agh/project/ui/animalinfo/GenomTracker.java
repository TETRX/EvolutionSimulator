package agh.project.ui.animalinfo;

import agh.project.game.animals.Animal;
import agh.project.game.animals.Genom;
import agh.project.game.information.AnimalData;
import agh.project.game.information.IMapObserver;
import agh.project.game.information.IObservableMap;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.ui.animalinfo.animalinfogetters.IAnimalInfoGetter;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenomTracker implements IMapObserver {
    private final AnimalInfoStage animalInfoStage;
    private final IObservableMap map;

    private final Genom genom;
    private final float maxEnergy;

    public GenomTracker(AnimalInfoStage animalInfoStage, Genom genom, float maxEnergy, IObservableMap map) {
        this.animalInfoStage = animalInfoStage;
        this.genom = genom;
        specimens = new HashSet<>();
        this.maxEnergy = maxEnergy;

        map.subscribe(this);
        this.map = map;
    }

    private final Set<AnimalData> specimens;

    @Override
    public void notify(MapState state) {
        List<AnimalInfoDisplay> newDisplays = new ArrayList<>();
        for (MapLocation location :
                state.animalData.keySet()) {
            for (AnimalData animalData :
                    state.animalData.get(location)) {
                if (animalData.genom.equals(genom) && !specimens.contains(animalData)){
                    specimens.add(animalData);

                    AnimalInfoDisplay animalInfoDisplay = new AnimalInfoDisplay(animalData,state,maxEnergy);

                    animalInfoDisplay.trackMoreInfo();
                    newDisplays.add(animalInfoDisplay);
                    map.subscribe(animalInfoDisplay);
                }
            }
        }
        if(!newDisplays.isEmpty()){
            Platform.runLater(() -> {
                animalInfoStage.addAnimalInfos(newDisplays);
            });
        }
    }
}

package agh.project.ui.animalinfo;

import agh.project.game.information.AnimalData;
import agh.project.game.information.IMapObserver;
import agh.project.game.information.IObservableMap;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.ui.animalinfo.animalinfogetters.*;
import agh.project.ui.simulation.GraphicalAnimal;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.*;


public class AnimalInfoDisplay extends GridPane implements IMapObserver {
    private final AnimalData startingAnimalData;

    private AnimalData latestAnimalData;

    private GraphicalAnimal animal;

    private static final float ANIMAL_IMAGE_SIZE = 100;

    private static final Image ANIMAL_IMAGE = GraphicalAnimal.getAnimalImage(ANIMAL_IMAGE_SIZE,ANIMAL_IMAGE_SIZE);
    private final float animalMaxEnergy;

    private static final Image DEATH_MARKER_IMAGE = DeadMarker.getImage(ANIMAL_IMAGE_SIZE,ANIMAL_IMAGE_SIZE);

    private boolean dead;

    private final GridPane info;
    private Button moreInfo;

    private final List<IAnimalInfoGetter> infoGetters;
    
    private final Map<Integer,Node> infoNames, infoValues;

    public AnimalInfoDisplay(AnimalData animalData, MapState context, float animalMaxEnergy){
        infoValues = new HashMap<>();
        infoNames =new HashMap<>();
        this.startingAnimalData = animalData;
        this.animalMaxEnergy = animalMaxEnergy;

        dead=false;


        info = new GridPane();
        infoGetters = new ArrayList<>();
        infoGetters.add(new GenomGetter());

        updateDisplay(animalData, context);

        moreInfo = new Button("Track more info");
        moreInfo.setOnAction((event -> {
            trackMoreInfo();
        }));
        info.add(moreInfo,1, info.getRowCount());

        add(info,1,0);
    }

    void trackMoreInfo(){
        infoGetters.add(new ChildrenNumberGetter(latestAnimalData));
        infoGetters.add(new DescendantNumberGetter(latestAnimalData));
        infoGetters.add(new DeathEpochGetter());

        stopOfferingMoreInfo();
    }

    void stopOfferingMoreInfo(){
        info.getChildren().remove(moreInfo);
    }

    private void updateDisplay(AnimalData animalData, MapState context){
        latestAnimalData=animalData;
        displayInfo(animalData,context);
        if(animal!=null){
            getChildren().removeAll(animal);
        }
        if (animalData!=null) {
            animal = new GraphicalAnimal(animalData, ANIMAL_IMAGE, animalMaxEnergy);
            add(animal, 0, 0);
        }
        else {
            stopOfferingMoreInfo();
            add(new DeadMarker(DEATH_MARKER_IMAGE),0,0);
            dead=true;
        }
    }

    private void displayInfo(AnimalData animalData, MapState context){
        int i = 0;
        for (IAnimalInfoGetter infoGetter :
                infoGetters) {
            String value = infoGetter.getValue(animalData,context);
            String name = infoGetter.getName()+": ";

            if(value!=null){
                Label nameLabel = new Label(name);
                if(infoNames.containsKey(i)){
                    info.getChildren().remove(infoNames.get(i));
                }
                infoNames.put(i, nameLabel);
                info.add(nameLabel,0,i);

                Label valueLabel = new Label(value);
                if(infoValues.containsKey(i)){
                    info.getChildren().remove(infoValues.get(i));
                }
                infoValues.put(i, valueLabel);
                info.add(valueLabel,1,i);
            }
            i++;
        }
    }

    public AnimalInfoDisplay(AnimalData animalData, MapState context, IObservableMap map, float animalMaxEnergy){
        this(animalData, context, animalMaxEnergy);
        map.subscribe(this);
    }

    @Override
    public void notify(MapState state) {
        if (dead){return;}
        AnimalData thisAnimal = null;
        for (MapLocation location :
                state.animalData.keySet()) {
            for (AnimalData animalData :
                    state.animalData.get(location)) {
                if (animalData.equals(this.startingAnimalData)) {
                    thisAnimal = animalData;
                }
                if (thisAnimal != null) {
                    break;
                }
            }
            if (thisAnimal != null) {
                break;
            }
        }
        AnimalData finalThisAnimal = thisAnimal;
        Platform.runLater(() -> updateDisplay(finalThisAnimal, state));
    }
}

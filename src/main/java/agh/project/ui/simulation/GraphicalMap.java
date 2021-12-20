package agh.project.ui.simulation;

import agh.project.game.information.AnimalData;
import agh.project.game.information.IMapObserver;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashSet;
import java.util.Set;

public class GraphicalMap extends GridPane implements IMapObserver {
    private final double fieldWidth, fieldHeight;
    private final int mapWidth, mapHeight;
    private GraphicalMapField[][] fields;
    private Set<MapLocation> nonEmptyFields;
    private final float maxEnergy;

    private Image animalImage;

    public GraphicalMap(int mapWidth, int mapHeight, int mapPixelWidth, int mapPixelHeight, float maxEnergy){
        super();
        this.fieldHeight = mapPixelHeight*1.0/mapHeight;
        this.fieldWidth = mapPixelWidth*1.0/mapWidth;

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;

        this.fields = new GraphicalMapField[mapHeight][mapWidth];
        setDimensions(mapWidth,mapHeight);
        setGridLinesVisible(true);
        nonEmptyFields = new HashSet<>();
        this.maxEnergy = mapHeight;

        animalImage = GraphicalAnimal.getAnimalImage(fieldWidth, fieldHeight);
    }

    private GraphicalMapField getFieldFromLocation(MapLocation location){
        return fields[location.x][mapWidth-location.y-1];
    }

    private void setDimensions(int width, int height){
        for(int i=0;i<width;i++) {
            getColumnConstraints().add(new ColumnConstraints(fieldWidth));
        }
        for(int i=0;i<height;i++) {
            getRowConstraints().add(new RowConstraints(fieldHeight));
        }

        for(int i=0;i<width;i++) {
            for(int j=0; j<height; j++){
                GraphicalMapField field = new GraphicalMapField();
                this.fields[j][i]=field;
                add(field,j,i);
            }
        }
    }

//    private Long lastDrawTime = null;

    private void drawMapState(MapState mapState) {
//        long newDrawTime = System.currentTimeMillis();
//        if(lastDrawTime!=null){
//            System.out.println("Time between draws: " + (newDrawTime-lastDrawTime));
//        }
//        lastDrawTime = newDrawTime;
        for (MapLocation mapLocation :
                mapState.newGrassLocations) {
            getFieldFromLocation(mapLocation).setGrass();
        }
        for (MapLocation mapLocation :
                mapState.noLongerGrassLocations) {
            getFieldFromLocation(mapLocation).setNoGrass();
        }

        for (MapLocation location :
                nonEmptyFields) {
            getFieldFromLocation(location).clearAnimals();
        }
        nonEmptyFields.clear();

        for (MapLocation mapLocation: mapState.animalData.keySet()) {
            for (AnimalData animalData :
                    mapState.animalData.get(mapLocation)) {
                GraphicalMapField mapField = getFieldFromLocation(mapLocation);
                GraphicalAnimal graphicalAnimal = new GraphicalAnimal(animalData, animalImage, maxEnergy);
                mapField.addAnimal(graphicalAnimal);
                nonEmptyFields.add(mapLocation);
            }
        }
    }

//    private Long lastNotifyTime = null;

    @Override
    public void notify(MapState state) {
//        long newNotifyTime = System.currentTimeMillis();
//        if(lastNotifyTime!=null){
//            System.out.println("Time between notifies: " + (newNotifyTime-lastNotifyTime));
//        }
//        lastNotifyTime = newNotifyTime;
        Platform.runLater(()->drawMapState(state));
    }
}

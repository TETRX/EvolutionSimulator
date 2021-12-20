package agh.project.ui.simulation;

import agh.project.ui.simulation.GraphicalAnimal;
import javafx.scene.CacheHint;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class GraphicalMapField extends StackPane {
    public List<GraphicalAnimal> getAnimals() {
        return animals;
    }

    private List<GraphicalAnimal> animals;

    public GraphicalMapField(){
        super();
        setNoGrass();
        animals = new ArrayList<>();
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public void setNoGrass(){
        setStyle("-fx-background-color: #C2B280;");
    }

    public void setGrass(){
        setStyle("-fx-background-color: #118D03;");
    }

    public void addAnimal(GraphicalAnimal animal){
        getChildren().add(animal);
        animals.add(animal);
    }

    public void clearAnimals(){
        getChildren().removeAll(animals);
    }
}

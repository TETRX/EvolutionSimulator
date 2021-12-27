package agh.project.ui.animalinfo;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class AnimalInfoStage extends Stage {
    private static final float MAX_HEIGHT = 800;
    private HBox hBox;

    public AnimalInfoStage(List<AnimalInfoDisplay> animalInfos){
        hBox = new HBox();
        hBox.getChildren().addAll(animalInfos);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);

        setScene(new Scene(scrollPane));

        setMaxHeight(MAX_HEIGHT);
    }

    public void addAnimalInfos(List<AnimalInfoDisplay> animalInfos){
        hBox.getChildren().addAll(animalInfos);
        sizeToScene();
    }
}

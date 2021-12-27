package agh.project.ui.animalinfo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeadMarker extends ImageView {
    public DeadMarker(Image image){
        super(image);
    }

    public static final String DEATH_MARKER_IMAGE_LOC = "/images/deadMarker.png";

    public static Image getImage(double width, double height){
        return new Image(DEATH_MARKER_IMAGE_LOC, width, height, false, false);
    }
}

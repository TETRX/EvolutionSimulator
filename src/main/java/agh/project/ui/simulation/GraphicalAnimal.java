package agh.project.ui.simulation;

import agh.project.game.information.AnimalData;
import agh.project.game.map.MapOrientation;
import javafx.scene.CacheHint;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GraphicalAnimal extends ImageView {
    public static final String ANIMAL_IMAGE_LOC = "/images/creatureMarker.png";
    private final float maxEnergy;

    public static Image getAnimalImage(double width, double height){
        return new Image(ANIMAL_IMAGE_LOC, width, height, false, false);
    }

    public GraphicalAnimal(AnimalData animalState, Image image, float maxEnergy){
        super(image);
        this.maxEnergy = maxEnergy;
        this.setClip(new ImageView(getImage()));
        setOrientation(animalState.state.getOrientation());
        setHealthIndicator(Math.min(animalState.energy/maxEnergy,1.0f));
    }

    private void setOrientation(MapOrientation mapOrientation){
        setRotate(45*mapOrientation.orientation);
    }

    private void setHealthIndicator(float percentage){

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);

        Blend blend = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0,
                        this.getImage().getWidth(),
                        this.getImage().getHeight(),
                        getColorOnRedGreenScale(percentage)
                )
        );
        this.effectProperty().setValue(blend);
    }

    private Color getColorOnRedGreenScale(float pointOnScale){
        return Color.rgb(Math.round(255*(1-pointOnScale)), Math.round( 255*(pointOnScale)), 0);
    }
}

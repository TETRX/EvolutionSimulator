package agh.project.game.reproduction;

public class MagicalReproductionEvent implements IReproductionEvent {
    private final int num;

    public MagicalReproductionEvent(int num) {
        this.num = num;
    }

    @Override
    public String getDescription() {
        return "Magical reproduction number: "+this.num +" occured";
    }
}

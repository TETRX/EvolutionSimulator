package agh.project.game.information;

public enum Phase {
    MOVEMENT("Movement"), FEEDING("Feeding"), REPRODUCTION("Reproduction"), GROWTH("Growth"), STARVING("Starving");

    private final String name;

    Phase(String name) {
        this.name = name;
    }
}

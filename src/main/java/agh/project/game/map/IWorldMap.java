package agh.project.game.map;

public interface IWorldMap {
    void reproducePhase();
    void movementPhase();
    void feedPhase();
    void growPhase();
    void starvePhase();
    void ageUp();
}

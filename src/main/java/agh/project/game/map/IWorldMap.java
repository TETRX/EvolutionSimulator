package agh.project.game.map;

import agh.project.game.information.IObservableMap;

public interface IWorldMap extends IObservableMap {
    void reproducePhase();
    void movementPhase();
    void feedPhase();
    void growPhase();
    void starvePhase();
    void ageUp();
}

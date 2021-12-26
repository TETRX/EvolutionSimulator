package agh.project.game.information;

public interface IObservableMap {
    void subscribe(IMapObserver observer);
    void unsubscribe(IMapObserver observer);
    void notifyObservers();
}

package agh.project.stats;

import agh.project.game.information.MapState;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleStatTracker <T>{
    private final List<StatObserver<? super T>> observers;

    public List<T> getStatHistory() {
        return statHistory;
    }

    private final List<T> statHistory;

    public SingleStatTracker()
    {
        observers = new ArrayList<>();
        statHistory = new ArrayList<>();
    }

    public void subscribe(StatObserver<? super T> observer){
        observers.add(observer);
    }

    public abstract T calculateUpdate(MapState mapState);

    public T update(MapState mapState){
        T updateValue = calculateUpdate(mapState);
        for (StatObserver<? super T> observer :
                observers) {
            observer.update(updateValue);
        }
        statHistory.add(updateValue);
        System.out.println(statName()+": "+updateValue);
        return updateValue;
    }

    public abstract String statName();
}

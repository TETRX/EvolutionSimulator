package agh.project.stats;

import agh.project.game.information.IMapObserver;
import agh.project.game.information.MapState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatsTracker implements IMapObserver {
    private final List<SingleStatTracker<?>> singleStatTrackers;

    public StatsTracker(List<SingleStatTracker<?>> singleStatTrackers) {
        this.singleStatTrackers = singleStatTrackers;
    }


    @Override
    public void notify(MapState state) {
        for (SingleStatTracker<?> singleStatTracker :
                singleStatTrackers) {
            singleStatTracker.update(state);
        }
    }




}

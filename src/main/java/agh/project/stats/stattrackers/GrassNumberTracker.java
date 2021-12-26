package agh.project.stats.stattrackers;

import agh.project.game.information.MapState;
import agh.project.stats.SingleStatTracker;

public class GrassNumberTracker extends SingleStatTracker<Integer> {
    @Override
    public Integer calculateUpdate(MapState mapState) {
        return mapState.foodData.size();
    }

    @Override
    public String statName() {
        return "Grass fields number";
    }

}

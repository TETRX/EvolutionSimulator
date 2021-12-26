package agh.project.stats.stattrackers;

import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.stats.SingleStatTracker;

public class AnimalNumberTracker extends SingleStatTracker<Integer> {

    @Override
    public Integer calculateUpdate(MapState mapState) {
        int n =0;
        for (MapLocation location :
                mapState.animalData.keySet()) {
            n+=mapState.animalData.get(location).size();
        }
        return n;
    }

    @Override
    public String statName() {
        return "Animal number";
    }
}

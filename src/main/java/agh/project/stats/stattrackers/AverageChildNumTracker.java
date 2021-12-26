package agh.project.stats.stattrackers;

import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.stats.SingleStatTracker;
import agh.project.stats.StatsTracker;

public class AverageChildNumTracker extends SingleStatTracker<Float> {
    @Override
    public Float calculateUpdate(MapState mapState) {
        int animalNumber = 0;
        int totalChildren = 0;
        for (MapLocation location :
                mapState.animalData.keySet()) {
            animalNumber+=mapState.animalData.get(location).size();
            for (AnimalData animal :
                    mapState.animalData.get(location)) {
                totalChildren+=animal.childrenNumber;
            }
        }
        return animalNumber!=0 ? ((float) totalChildren)/animalNumber : 0f;
    }

    @Override
    public String statName() {
        return "Average child number";
    }
}

package agh.project.stats.stattrackers;

import agh.project.game.animals.Genom;
import agh.project.game.information.AnimalData;
import agh.project.game.information.MapState;
import agh.project.game.map.MapLocation;
import agh.project.stats.SingleStatTracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DominatingGenomTracker extends SingleStatTracker<Genom> {
    @Override
    public Genom calculateUpdate(MapState mapState) {
        Map<Genom,Integer> counter = new HashMap<>();
        Genom dominant = null;
        Integer dominantNum = 0;
        for (MapLocation location :
                mapState.animalData.keySet()) {
            for (AnimalData animal :
                    mapState.animalData.get(location)) {
                counter.put(animal.genom, counter.getOrDefault(animal.genom, 0)+1);
                if(counter.get(animal.genom)>dominantNum || dominant==null){
                    dominant=animal.genom;
                    dominantNum = counter.get(animal.genom);
                }
            }
        }
        return dominant;
    }

    @Override
    public String statName() {
        return "Dominating genom";
    }
}

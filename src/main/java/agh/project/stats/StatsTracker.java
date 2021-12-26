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

    private String getCSVLine(List<String> columnVals){
        StringBuilder line = new StringBuilder();
        for (String columnVal :
                columnVals) {
            line.append(columnVal).append("; ");
        }
        return line.toString();
    }

    public String getCSVData(){
        List<List<String>> statHistories = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("age");
        for (SingleStatTracker<?> singleStatTracker:
             singleStatTrackers) {
            List<String> statHistory = singleStatTracker.getStatHistory().stream().map(x -> {
                if (x!=null){
                    return x.toString();
                }
                return "null";
            }).collect(Collectors.toList());
            statHistories.add(statHistory);

            columnNames.add(singleStatTracker.statName());
        }
        StringBuilder csvData = new StringBuilder();
        csvData.append(getCSVLine(columnNames)).append("\n");

        for (int i = 0; i < statHistories.get(0).size(); i++) {
            List<String> line = new ArrayList<>();
            line.add(String.valueOf(i));
            for (List<String> stat :
                    statHistories) {
                line.add(stat.get(i));
            }
            csvData.append(getCSVLine(line)).append("\n");
        }

        return csvData.toString();
    }
}

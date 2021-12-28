package agh.project.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVStatSerializer {
    private final List<SingleStatTracker<? extends Number>> singleStatTrackers;

    public CSVStatSerializer(SingleStatTracker<? extends Number>... singleStatTrackers) {
        this.singleStatTrackers = new ArrayList<>();
        this.singleStatTrackers.addAll(List.of(singleStatTrackers));
    }

    private String getCSVLine(List<String> columnVals) {
        StringBuilder line = new StringBuilder();
        for (String columnVal :
                columnVals) {
            line.append(columnVal).append("; ");
        }
        return line.toString();
    }

    public String getCSVData() {
        List<List<String>> statHistories = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        List<String> averages = new ArrayList<>();
        averages.add("average");

        columnNames.add("age");
        for (SingleStatTracker<?> singleStatTracker :
                singleStatTrackers) {
            List<?> singleStatTrackerStatHistory = singleStatTracker.getStatHistory();
            List<String> statHistory = singleStatTrackerStatHistory.stream().map(x -> {
                if (x != null) {
                    return x.toString();
                }
                return "null";
            }).collect(Collectors.toList());
            statHistories.add(statHistory);

            float average = 0;
            for (Object number:
                    singleStatTrackerStatHistory) {
                try{
                    float converted = ((Number) number).floatValue();
                    average+=converted;
                }catch (ClassCastException e) {
                    System.out.println(e.getMessage());
                    average += 0;
                }
            }
            average/=Math.max(singleStatTrackerStatHistory.size(),1);
            averages.add(String.valueOf(average));
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
        csvData.append(getCSVLine(averages)).append("\n");

        return csvData.toString();
    }
}

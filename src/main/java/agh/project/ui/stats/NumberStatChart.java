package agh.project.ui.stats;

import agh.project.stats.SingleStatTracker;
import agh.project.stats.StatObserver;
import javafx.application.Platform;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class NumberStatChart extends LineChart<Number, Number> implements StatObserver<Number> {
    public NumberStatChart(Axis<Number> ageAxis, Axis<Number> numberAxis) {
        super(ageAxis, numberAxis);
    }

    private NumberStatChart(Axis<Number> ageAxis, Axis<Number> numberAxis, SingleStatTracker<? extends Number> statTracker) {
        this(ageAxis,numberAxis);
        age = 0;
        data = new Series<>();
        data.setName(statTracker.statName());
        getData().add(data);
        statTracker.subscribe(this);
    }

    private int age;
    private Series<Number,Number> data;

    public static NumberStatChart getNumberStatChart(SingleStatTracker<? extends Number> statTracker){
        NumberAxis Xaxis = new NumberAxis();
        Xaxis.setLabel("age");
        NumberAxis Yaxis = new NumberAxis();
        Yaxis.setLabel(statTracker.statName());
        NumberStatChart numberStatChart = new NumberStatChart(Xaxis, Yaxis, statTracker);
        numberStatChart.setTitle(statTracker.statName());
        return numberStatChart;
    }

    private void updateGraphics(Number newData){
        data.getData().add(new Data<>(age,newData));
        age++;
    }

    @Override
    public void update(Number newData) {
        Platform.runLater(() -> updateGraphics(newData));
    }
}

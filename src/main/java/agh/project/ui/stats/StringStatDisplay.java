package agh.project.ui.stats;

import agh.project.stats.SingleStatTracker;
import agh.project.stats.StatObserver;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StringStatDisplay extends GridPane implements StatObserver<Object> {
    private Label statDisplay;

    public StringStatDisplay(SingleStatTracker<? extends Object> statTracker){
        add(new Label(statTracker.statName()+": "), 0 ,0);
        Object lastNonNullStat = statTracker.getLastNonNull();
        updateStat(lastNonNullStat);

        statTracker.subscribe(this);
    }

    private void updateStat(Object newStat){
        if (statDisplay!=null) {
            getChildren().remove(statDisplay);
        }
        statDisplay = new Label(newStat!=null ? newStat.toString() : "-");
        add(statDisplay,1,0);
    }

    @Override
    public void update(Object newData) {
        Platform.runLater(() -> updateStat(newData));
    }
}

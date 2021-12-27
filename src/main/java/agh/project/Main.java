package agh.project;

import agh.project.game.Simulation;
import agh.project.game.animals.Animal;
import agh.project.game.animals.Genom;
import agh.project.game.map.AnimalState;
import agh.project.game.map.MapOrientation;
import agh.project.game.map.WorldMap;
import agh.project.game.movement.BarrierMovementRules;
import agh.project.game.movement.Bounds;
import agh.project.game.movement.IMovementRules;
import agh.project.game.movement.TeleportMovementRules;
import agh.project.game.reproduction.IReproductionRules;
import agh.project.game.reproduction.MagicalReproductionRules;
import agh.project.game.reproduction.StandardReproductionRules;
import agh.project.stats.SingleStatTracker;
import agh.project.stats.StatsTracker;
import agh.project.stats.stattrackers.*;
import agh.project.ui.animalinfo.AnimalInfoStage;
import agh.project.ui.animalinfo.GenomTracker;
import agh.project.ui.simulation.GraphicalMap;
import agh.project.ui.stats.NumberStatChart;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;

public class Main extends Application{
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }


    private void addStartupRow(GridPane startup, String prompt, Region input){
        startup.add(new Label(prompt),0, startup.getRowCount());
        startup.add(input, 1, startup.getRowCount()-1);
    }

    private Slider initStartupSlider(float max, float defaultValue){
        Slider slider = new Slider(0, max, defaultValue);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f*max);
        slider.setBlockIncrement(0.1f*max);
        return slider;
    }


    private List<StartingConfiguration> getStartingConfigurations(){
        GridPane startUpMenu = new GridPane();

        UnaryOperator<TextFormatter.Change> positiveIntegerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")){
                return change;
            }
            else return null;
        };

        TextField widthTextField = new TextField();
        widthTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), StartingConfiguration.DEFAULT_WIDTH, positiveIntegerFilter));

        TextField heightTextField = new TextField();
        heightTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), StartingConfiguration.DEFAULT_HEIGHT, positiveIntegerFilter));

        TextField animalNumberTextField = new TextField();
        animalNumberTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), StartingConfiguration.DEFAULT_ANIMAL_N, positiveIntegerFilter));

        Slider startEnergySlider = initStartupSlider(StartingConfiguration.MAX_START_ENERGY, StartingConfiguration.DEFAULT_START_ENERGY),
                moveEnergySlider = initStartupSlider(StartingConfiguration.MAX_MOVE_ENERGY, StartingConfiguration.DEFAULT_MOVE_ENERGY),
                plantEnergySlider = initStartupSlider(StartingConfiguration.MAX_PLANT_ENERGY, StartingConfiguration.DEFAULT_PLANT_ENERGY),
                jungleRatioSlider = initStartupSlider(1.0f,StartingConfiguration.DEFAULT_JUNGLE_RATIO);

        addStartupRow(startUpMenu, "Map width:", widthTextField);
        addStartupRow(startUpMenu, "Map height:", heightTextField);
        addStartupRow(startUpMenu, "Animal number:", animalNumberTextField);
        addStartupRow(startUpMenu, "Start energy:", startEnergySlider);
        addStartupRow(startUpMenu, "Move energy:", moveEnergySlider);
        addStartupRow(startUpMenu, "Plant energy:", plantEnergySlider);
        addStartupRow(startUpMenu, "Jungle ratio:", jungleRatioSlider);

        ChoiceBox<String> reproductionRulesChoiceBox = new ChoiceBox<>();
        String standardReproductionChoice = "Standard", magicalReproductionChoice = "Magical";
        reproductionRulesChoiceBox.getItems().add(standardReproductionChoice);
        reproductionRulesChoiceBox.getItems().add(magicalReproductionChoice);
        reproductionRulesChoiceBox.setValue(standardReproductionChoice);

        ChoiceBox<String> movementRulesChoiceBox = new ChoiceBox<>();
        String barrierMovementChoice = "Barrier", teleportMovementChoice = "Teleport", bothMovementChoice = "Both";
        movementRulesChoiceBox.getItems().add(barrierMovementChoice);
        movementRulesChoiceBox.getItems().add(teleportMovementChoice);
        movementRulesChoiceBox.getItems().add(bothMovementChoice);
        movementRulesChoiceBox.setValue(barrierMovementChoice);

        addStartupRow(startUpMenu, "Movement rules: ", movementRulesChoiceBox);
        addStartupRow(startUpMenu, "Reproduction rules: ", reproductionRulesChoiceBox);

        List<StartingConfiguration> startingConfigurations = new ArrayList<>();
        Button run = new Button("Run");

        run.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                int mapWidth = Integer.parseInt(widthTextField.getText());
                int mapHeight = Integer.parseInt(heightTextField.getText());
                int startingAnimalNumber = Integer.parseInt(animalNumberTextField.getText());

                float startEnergy = (float) startEnergySlider.getValue();
                float moveEnergy = (float) moveEnergySlider.getValue();
                float plantEnergy = (float) plantEnergySlider.getValue();
                float jungleRatio = (float) jungleRatioSlider.getValue();

                Bounds bounds = new Bounds(0,mapHeight,0,mapWidth);

                List<IMovementRules> movementRules = new ArrayList<>();
                if(Objects.equals(movementRulesChoiceBox.getValue(), barrierMovementChoice) || (Objects.equals(movementRulesChoiceBox.getValue(), bothMovementChoice))){
                    movementRules.add(new BarrierMovementRules(bounds));
                }
                if(Objects.equals(movementRulesChoiceBox.getValue(), teleportMovementChoice) || (Objects.equals(movementRulesChoiceBox.getValue(), bothMovementChoice))){
                    movementRules.add(new TeleportMovementRules(bounds));
                }
                IReproductionRules reproductionRules = null;
                if(Objects.equals(reproductionRulesChoiceBox.getValue(), standardReproductionChoice)) {
                    reproductionRules = new StandardReproductionRules(random,startEnergy/4);
                }
                if(Objects.equals(reproductionRulesChoiceBox.getValue(), magicalReproductionChoice)){
                    reproductionRules = new MagicalReproductionRules(random,startEnergy/4, 3, bounds);
                }

                for (IMovementRules movementRule :
                        movementRules) {
                    startingConfigurations.add(new StartingConfiguration(mapWidth,mapHeight, startingAnimalNumber, startEnergy,moveEnergy,plantEnergy,jungleRatio,reproductionRules,movementRule));
                }

                ((Stage)((Button)e.getSource()).getScene().getWindow()).close();
            }
        });
        startUpMenu.add(run,0,startUpMenu.getRowCount());

        Scene scene = new Scene(startUpMenu);
        Stage stage = new Stage();


        stage.setScene(scene);
        stage.showAndWait();
        return startingConfigurations;
    }

    private void initStartingConfiguration(StartingConfiguration startingConfiguration){
        // init simulation
        Bounds bounds = new Bounds(0,startingConfiguration.height,0,startingConfiguration.width);

        Map<Animal, AnimalState> startingAnimals = new HashMap<>();
        for (int i = 0; i < startingConfiguration.startingAnimalNumber; i++) {
            Genom genom = new Genom(random);
            genom.randomizeGenom();
            startingAnimals.put(new Animal(startingConfiguration.startEnergy, genom, random),
                    new AnimalState(MapOrientation.getRandom(random), bounds.getRandomWithinBounds(random)));
        }
        WorldMap worldMap = new WorldMap(
                startingConfiguration.startEnergy,
                startingConfiguration.moveEnergy,
                startingConfiguration.movementRules,
                startingConfiguration.reproductionRules,
                startingConfiguration.width,
                startingConfiguration.height,
                startingConfiguration.jungleRatio,
                random,
                startingAnimals,
                startingConfiguration.plantEnergy);
        for (Animal animal :
                startingAnimals.keySet()) {
            animal.setMap(worldMap);
        }
        Simulation simulation = new Simulation(worldMap);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                simulation.run();
            }
        });

        // init stattracking
        List<SingleStatTracker<?>> stats = new ArrayList<>();
        AnimalNumberTracker animalNumberTracker = new AnimalNumberTracker();
        stats.add(animalNumberTracker);
        AverageChildNumTracker averageChildNumTracker = new AverageChildNumTracker();
        stats.add(averageChildNumTracker);
        AverageEnergyTracker averageEnergyTracker = new AverageEnergyTracker();
        stats.add(averageEnergyTracker);
        AverageLifetimeTracker averageLifetimeTracker = new AverageLifetimeTracker();
        stats.add(averageLifetimeTracker);
        DominatingGenomTracker dominatingGenomTracker = new DominatingGenomTracker();
        stats.add(dominatingGenomTracker);
        GrassNumberTracker grassNumberTracker = new GrassNumberTracker();
        stats.add(grassNumberTracker);
        StatsTracker statsTracker = new StatsTracker(stats);
        worldMap.subscribe(statsTracker);

        //init stat charts
        NumberStatChart animalNumberChart = NumberStatChart.getNumberStatChart(animalNumberTracker);
        NumberStatChart averageChildNumChart = NumberStatChart.getNumberStatChart(averageChildNumTracker);
        NumberStatChart averageEnergyChart = NumberStatChart.getNumberStatChart(averageEnergyTracker);
        NumberStatChart averageLifetimeChart = NumberStatChart.getNumberStatChart(averageLifetimeTracker);
        NumberStatChart grassNumberChart = NumberStatChart.getNumberStatChart(grassNumberTracker);

        VBox charts = new VBox();
        charts.getChildren().addAll(animalNumberChart,averageChildNumChart,averageEnergyChart,averageLifetimeChart,grassNumberChart);
        ScrollPane chartScrollPane = new ScrollPane();
        chartScrollPane.setContent(charts);
        chartScrollPane.setLayoutX(800);
        chartScrollPane.setLayoutY(0);
        chartScrollPane.setPrefHeight(800);

        HBox controlPanel = new HBox();
        AtomicBoolean paused = new AtomicBoolean(false);
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            if (paused.get()){
                simulation.unpause();
                ((Button)event.getSource()).setText("Pause");
                paused.set(false);
            }
            else {
                simulation.pause();
                ((Button)event.getSource()).setText("Unpause");
                paused.set(true);
            }
        });

        //start graphics
        Group root = new Group();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        Button saveButton = new Button("Save stats");
        saveButton.setOnAction(event -> {
            String csvData = statsTracker.getCSVData();
            TextInputDialog filenameDialog = new TextInputDialog(Paths.get(System.getProperty("user.dir"), "stats.csv").toString());
            filenameDialog.setHeaderText("Choose a filepath for text");

            Button okButton = (Button) filenameDialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, actionEvent -> {
                String filepath = filenameDialog.getEditor().getText();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(filepath));
                    writer.write(csvData);
                    writer.close();
                } catch (IOException e) {
                    actionEvent.consume();
                }

            });
            filenameDialog.show();

        });

        Button trackDominatingGenom = new Button("Track dominating genom");
        trackDominatingGenom.setOnAction((event -> {
            AnimalInfoStage animalInfoStage = new AnimalInfoStage(new ArrayList<>());
            new GenomTracker(animalInfoStage, dominatingGenomTracker.getLastNonNull(), startingConfiguration.startEnergy, worldMap);
            animalInfoStage.show();
        }));

        controlPanel.getChildren().add(pauseButton);
        controlPanel.getChildren().add(saveButton);
        controlPanel.getChildren().add(trackDominatingGenom);
        controlPanel.setLayoutX(0);
        controlPanel.setLayoutY(800);


        GraphicalMap graphicalMap = new GraphicalMap(startingConfiguration.width,
                startingConfiguration.height,
                800,
                800,
                startingConfiguration.startEnergy,
                worldMap);
        worldMap.subscribe(graphicalMap);
        graphicalMap.setLayoutX(0);
        graphicalMap.setLayoutY(0);
        thread.start();

        root.getChildren().add(graphicalMap);
        root.getChildren().add(chartScrollPane);
        root.getChildren().add(controlPanel);
        stage.setScene(scene);
        stage.show();
    }

    private final static Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<StartingConfiguration> startingConfigurations = getStartingConfigurations();
        for (StartingConfiguration startingConfiguration : startingConfigurations){
            initStartingConfiguration(startingConfiguration);
        }
    }
}

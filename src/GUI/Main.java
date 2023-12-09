package GUI;

import Tomasulo.Simulator;
import Tomasulo.State;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static TextField[] textFields = new TextField[10];
    public static String instructions;
    static ArrayList<State> cycleStates;
    static int currCycle = 8;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Tomasulo Simulator");

        // Create a label to provide focus to and avoid text highlighting
        Label focusLabel = new Label("");
        focusLabel.setFocusTraversable(true);

        // Create a text field with the title
        TextField titleTextField = new TextField("Tomatulo");
        titleTextField.setFont(Font.font("Pacifico", FontWeight.BOLD, FontPosture.REGULAR, 28));
        titleTextField.setEditable(false);
        titleTextField.setBackground(new Background(
                new BackgroundFill(Color.rgb(255, 231, 189), null, null)));
        titleTextField.setStyle("-fx-text-fill: red;");
        titleTextField.setAlignment(Pos.CENTER);
        titleTextField.setMinWidth(200);
        titleTextField.setMinHeight(70);
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);
        h.getChildren().add(focusLabel);
        h.getChildren().add(titleTextField);
        BorderPane p = new BorderPane();
        p.setTop(h);


        // Create a text field for user input
        TextArea userInputTextField = new TextArea();
        userInputTextField.setFont(Font.font("Arial", 20));
        userInputTextField.setBackground(
                new Background(new BackgroundFill(Color.rgb(255, 231, 189), null, null)));
        userInputTextField.setStyle("-fx-text-fill: black;");
        userInputTextField.setMaxWidth(500);
        userInputTextField.setMaxHeight(200);
        userInputTextField.setPromptText("Enter your instructions here");
        p.setCenter(userInputTextField);
        String userInput = userInputTextField.getText();

        VBox v = new VBox();
        v.setSpacing(10);
        v.setPadding(new Insets(50));
        String arr[] = {"ADD LATENCY", "SUB LATENCY", "MUL LATENCY", "DIV LATENCY", "LOAD LATENCY", "STORE LATENCY", "ADD/SUB RS SIZE", "MUL/DIV RS SIZE", "LOAD RS SIZE", "STORE RS SIZE"};
        for (int i = 0; i < 10; i++) {
            textFields[i] = new TextField();
            textFields[i].setFont(Font.font("Arial", 16));
            textFields[i].setBackground(new Background(
                    new BackgroundFill(Color.BEIGE, null, null)));
            textFields[i].setStyle("-fx-text-fill: black;");
            textFields[i].setMaxWidth(170);
            textFields[i].setMinHeight(25);

            textFields[i].setPromptText(arr[i]);
            v.getChildren().add(textFields[i]);
        }
        v.setAlignment(Pos.CENTER);
        p.setLeft(v);

        // Create a "Simulate" button
        Button simulateButton = new Button("Run");
        simulateButton.setMinWidth(100);
        simulateButton.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox(simulateButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(0, 0, 100, 0));
        p.setBottom(buttonContainer);


        // Load the background image
        Image backgroundImage = new Image("tomatoes.png"); // Assuming the image is in the same folder as your Java file

        // Create a background image
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, false));

        // Set the background image to the layout
        p.setBackground(new Background(background));

        // Create the scene with the layout
        Scene scene = new Scene(p, 2000, 1000);


        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the window
        primaryStage.show();

        // Request focus for the label, removing focus from the text fields
        focusLabel.requestFocus();

        VBox p2 = new VBox();
        p2.setBackground(new Background(background));
        Scene scene2 = new Scene(p2, 2000, 1000);


        TableView tableInputs = new TableView<>();

        // Create columns
        TableColumn<Pair, String> inputCol = new TableColumn<>("input");
        inputCol.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> value = new TableColumn<>("value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableInputs.getColumns().add(inputCol);
        tableInputs.getColumns().add(value);

        TableView memory = new TableView();
        TableColumn<Pair, String> address = new TableColumn<>("address");
        address.setCellValueFactory(new PropertyValueFactory<>("key"));
        TableColumn<Pair, String> memValue = new TableColumn<>("value");
        memValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        memory.getColumns().addAll(address, memValue);


        TableView floatRegisterFile = new TableView();

        TableColumn<Pair, String> floatRegisterLabel = new TableColumn<>("Register");
        floatRegisterLabel.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> floatRegisterValue = new TableColumn<>("Value");
        floatRegisterValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Pair, String> floatRegisterTag = new TableColumn<>("Waiting");
        floatRegisterTag.setCellValueFactory(new PropertyValueFactory<>("value2"));
        floatRegisterFile.getColumns().addAll(floatRegisterLabel, floatRegisterValue, floatRegisterTag);

        TableView integerRegisterFile = new TableView();

        TableColumn<Pair, String> integerRegisterLabel = new TableColumn<>("Register");
        integerRegisterLabel.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> integerRegisterValue = new TableColumn<>("Value");
        integerRegisterValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Pair, String> integerRegisterTag = new TableColumn<>("Waiting");
        integerRegisterTag.setCellValueFactory(new PropertyValueFactory<>("value2"));
        integerRegisterFile.getColumns().addAll(integerRegisterLabel, integerRegisterValue, integerRegisterTag);

        // Create a load buffer table
        TableView loadBuffer = new TableView();
        TableColumn<Pair, String> loadBufferLabel = new TableColumn("Label");
        loadBufferLabel.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> loadBufferAddress = new TableColumn("A");
        loadBufferAddress.setCellValueFactory(new PropertyValueFactory<>("value2"));

        TableColumn<Pair, String> loadBufferBusy = new TableColumn("Busy");
        loadBufferBusy.setCellValueFactory(new PropertyValueFactory<>("value"));
        loadBuffer.getColumns().addAll(loadBufferLabel, loadBufferBusy, loadBufferAddress);

        // Create a load buffer table
        TableView storeBuffer = new TableView();
        TableColumn<Pair, String> storeBufferLabel = new TableColumn("Label");
        storeBufferLabel.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> storeBufferAddress = new TableColumn("A");
        loadBufferAddress.setCellValueFactory(new PropertyValueFactory<>("value2"));

        TableColumn<Pair, String> storeBufferBusy = new TableColumn("Busy");
        storeBufferBusy.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Pair, String> storeBufferValue = new TableColumn("V");
        storeBufferValue.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<Pair, String> storeBufferTag = new TableColumn("Q");
        storeBufferTag.setCellValueFactory(new PropertyValueFactory<>("value4"));
        storeBuffer.getColumns().addAll(storeBufferLabel, storeBufferBusy, storeBufferAddress, storeBufferValue, storeBufferTag);


        TableView addSubRS = new TableView();

        TableColumn<Pair, String> addSubRSLabel = new TableColumn("Label");
        addSubRSLabel.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair, String> addSubRSOp = new TableColumn("OP");
        addSubRSOp.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Pair, String> addSubRSVj = new TableColumn("Vj");
        addSubRSVj.setCellValueFactory(new PropertyValueFactory<>("value2"));

        TableColumn<Pair, String> addSubRSVk = new TableColumn("Vk");
        addSubRSVk.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<Pair, String> addSubRSQj = new TableColumn("Qj");
        addSubRSQj.setCellValueFactory(new PropertyValueFactory<>("value4"));

        TableColumn<Pair, String> addSubRSQk = new TableColumn("Qk");
        addSubRSQk.setCellValueFactory(new PropertyValueFactory<>("value5"));

        TableColumn<Pair, String> addSubRSBusy = new TableColumn("Busy");
        addSubRSBusy.setCellValueFactory(new PropertyValueFactory<>("value6"));

        addSubRS.getColumns().addAll(addSubRSLabel, addSubRSOp, addSubRSVj, addSubRSVk, addSubRSQj, addSubRSQk, addSubRSBusy);

        //mul/div rs
        TableView mulDivRS = new TableView();

        mulDivRS.getColumns().addAll(addSubRSLabel, addSubRSOp, addSubRSVj, addSubRSVk, addSubRSQj, addSubRSQk, addSubRSBusy);

        // table for instructions
        TableView instructionsTable = new TableView();

        TableColumn<Pair,String> instructionsColumn = new TableColumn("Instruction");
        instructionsColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        TableColumn<Pair,String> instructionsStatus = new TableColumn("Issue");
        instructionsStatus.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Pair,String> instructionsStatus2 = new TableColumn("Start Execute");
        instructionsStatus2.setCellValueFactory(new PropertyValueFactory<>("value2"));

        TableColumn<Pair,String> instructionsStatus3 = new TableColumn("End Execute");
        instructionsStatus3.setCellValueFactory(new PropertyValueFactory<>("value3"));

        TableColumn<Pair,String> instructionsStatus4 = new TableColumn("Write Back");
        instructionsStatus4.setCellValueFactory(new PropertyValueFactory<>("value4"));

        instructionsTable.getColumns().addAll(instructionsColumn,instructionsStatus,instructionsStatus2,instructionsStatus3,instructionsStatus4);







        /* button */
        simulateButton.setOnAction(e -> {

            try {
                instructions = userInputTextField.getText();

                for (int i = 0; i < 10; i++) {
                    tableInputs.getItems().add(new Pair(arr[i], textFields[i].getText()));
                }
                Simulator.startFromGUI();
                cycleStates = Simulator.states;
                State curState = cycleStates.get(currCycle);

                for (int i = 0; i < curState.memoryValues.length; i++) {
                    memory.getItems().add(new Pair("MEM" + (i), curState.memoryValues[i] + ""));
                }

                for (int i = 0; i < curState.floatRegisterFile.length; i++) {
                    floatRegisterFile.getItems().add(new Pair("R" + (i), curState.floatRegisterFile[i].value + "", curState.floatRegisterFile[i].tag));
                }

                for (int i = 0; i < curState.integerRegisterFile.length; i++) {
                    integerRegisterFile.getItems().add(new Pair("R" + (i), (int)curState.integerRegisterFile[i].value + "", curState.integerRegisterFile[i].tag));
                }

                for (int i = 0; i < curState.loadReservationStation.length; i++) {
                    loadBuffer.getItems().add(new Pair("L" + (i), curState.loadReservationStation[i].busy + "", curState.loadReservationStation[i].tag));
                }

                for (int i = 0; i < curState.storeReservationStation.length; i++) {
                    storeBuffer.getItems().add(new Pair("S" + (i), curState.storeReservationStation[i].busy + "", curState.storeReservationStation[i].tag, curState.storeReservationStation[i].address + "", curState.storeReservationStation[i].Vj + "", curState.storeReservationStation[i].Qj + ""));
                }

                for (int i = 0; i < curState.addReservationStation.length; i++) {
                    addSubRS.getItems().add(new Pair("A" + (i), curState.addReservationStation[i].instruction != null ? curState.addReservationStation[i].instruction.getClass().getSimpleName() : ""
                            , curState.addReservationStation[i].getVj() + "", curState.addReservationStation[i].getVk() + "", curState.addReservationStation[i].getQj(), curState.addReservationStation[i].getQk(), curState.addReservationStation[i].busy + ""));
                }

                for (int i = 0; i < curState.multReservationStation.length; i++) {
                    mulDivRS.getItems().add(new Pair("M" + (i), curState.multReservationStation[i].instruction != null ? curState.multReservationStation[i].instruction.getClass().getSimpleName() : ""
                            , curState.multReservationStation[i].Vj + "", curState.multReservationStation[i].Vk + "", curState.multReservationStation[i].Qj, curState.multReservationStation[i].Qk, curState.multReservationStation[i].busy + ""));
                }
                String[] seperatedIns = instructions.split("\n");
                for (int i = 0; i < curState.program.size(); i++) {
                    instructionsTable.getItems().add(new Pair(seperatedIns[i],curState.program.get(i).issuedCycle+"",curState.program.get(i).executedCycle+"",curState.program.get(i).finishedECycle+"",curState.program.get(i).writtenCycle+""));
                }

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            primaryStage.setScene(scene2);

        });

        // latencies
        tableInputs.setMaxWidth(200);
        tableInputs.setMaxHeight(330);

        memory.setMaxHeight(330);
        memory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        floatRegisterFile.setMaxHeight(330);
        floatRegisterFile.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        integerRegisterFile.setMaxHeight(330);
        integerRegisterFile.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadBuffer.setMaxHeight(330);
        loadBuffer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        storeBuffer.setMaxWidth(330);
        storeBuffer.setMaxHeight(400);

        addSubRS.setMaxWidth(330);
        addSubRS.setMaxHeight(400);

        mulDivRS.setMaxWidth(330);
        mulDivRS.setMaxHeight(400);

        instructionsTable.setMaxWidth(330);
        instructionsTable.setMaxHeight(400);


        // create a label to show the current cycle
        Label cycleLabel = cycleLabel();

        HBox p3 = new HBox();
        p3.getChildren().addAll(cycleLabel, tableInputs, instructionsTable, integerRegisterFile,floatRegisterFile);
        p3.setSpacing(20);
        HBox p4 = new HBox();
        p4.getChildren().addAll(memory, loadBuffer, storeBuffer, addSubRS, mulDivRS);
        p4.setSpacing(20);

        p2.getChildren().addAll(p3, p4);

        p2.setSpacing(20);
    }

    private Label cycleLabel() {
        Label cycleLabel = new Label("Cycle " + currCycle);
        cycleLabel.setFont(Font.font("Arial", 25));
        cycleLabel.setBackground(new Background(
                new BackgroundFill(Color.BEIGE, null, null)));
        cycleLabel.setStyle("-fx-text-fill: black;");
        return cycleLabel;
    }

}

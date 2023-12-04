import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {
    static TextField[] textFields = new TextField[10];
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Set the window title
        
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
        TextField userInputTextField = new TextField();
        userInputTextField.setFont(Font.font("Arial", 16));
        userInputTextField.setBackground(
                new Background(new BackgroundFill(Color.BEIGE, null, null)));
        userInputTextField.setStyle("-fx-text-fill: black;");
        userInputTextField.setMaxWidth(500);
        userInputTextField.setMinHeight(200);
        userInputTextField.setPromptText("Enter your instructions here");
        userInputTextField.setAlignment(Pos.TOP_LEFT);
        p.setCenter(userInputTextField);
        // take the input text and put it in a string
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
        Button simulateButton = new Button("Simulate");
        simulateButton.setMinWidth(100);
        simulateButton.setAlignment(Pos.CENTER);
        
        HBox buttonContainer = new HBox(simulateButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(0, 0, 100, 0));
        p.setBottom(buttonContainer);
        
        
        // Load the background image
        Image backgroundImage = new Image("tomatoes.png"); // Assuming the image is in the same folder as your Java file
        
        // Create a background image
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, true, true, false));
        
        // Set the background image to the layout
        p.setBackground(new Background(background));
        
        // Create the scene with the layout
        Scene scene = new Scene(p, 1000, 565);
        
        
        // Set the scene to the stage
        primaryStage.setScene(scene);
        
        // Show the window
        primaryStage.show();
        
        // Request focus for the label, removing focus from the text fields
        focusLabel.requestFocus();
        
        StackPane p2 = new StackPane();
        p2.setBackground(new Background(background));
        Scene scene2 = new Scene(p2, 1000, 565);
        simulateButton.setOnAction(e -> {
            // Add your simulation logic here
            System.out.println("Simulation button clicked!");
            String instructions = userInputTextField.getText();
            int addLatency = Integer.parseInt(textFields[0].getText());
            int subLatency = Integer.parseInt(textFields[1].getText());
            int mulLatency = Integer.parseInt(textFields[2].getText());
            int divLatency = Integer.parseInt(textFields[3].getText());
            int loadLatency = Integer.parseInt(textFields[4].getText());
            int storeLatency = Integer.parseInt(textFields[5].getText());
            int addSubRS = Integer.parseInt(textFields[6].getText());
            int mulDivRS = Integer.parseInt(textFields[7].getText());
            int loadRS = Integer.parseInt(textFields[8].getText());
            int storeRS = Integer.parseInt(textFields[9].getText());
            
            primaryStage.setScene(scene2);
            
        });
    }
    
}
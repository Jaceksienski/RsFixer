package bestPackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * wywołanie:
 * AlertBoxUniversal.display("tytuł", "opis", "przycisk");
 */


public class AlertBoxUniversal {

    public static void display(String title, String message, String buttonMessage) {

        Stage window = new Stage();
        VBox layout = new VBox(12);
        Scene scene = new Scene(layout);
        Label labelExplanation = new Label(message);
        Button buttonBackToMenu = new Button(buttonMessage);

        buttonBackToMenu.setOnAction(e -> {
            window.close();
        });

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(280);
        window.setMinHeight(200);
        //window.setMaxWidth(220);

        layout.getChildren().addAll(labelExplanation, buttonBackToMenu);
        layout.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.showAndWait();
    }

    public static boolean display(String title, String message, String buttonYES, String buttonNO) {

        Stage window = new Stage();
        VBox layout = new VBox(12);
        Scene scene = new Scene(layout);
        Label labelExplanation = new Label(message);
        //labelExplanation.setMaxWidth(220);
        labelExplanation.setWrapText(true);
        Button buttonYes = new Button(buttonYES);
        Button buttonNo = new Button(buttonNO);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(buttonYes, buttonNo);

        final boolean[] clicked = new boolean[1];

        buttonYes.setOnAction(e -> {
            window.close();
            clicked[0] = true;
        });

        buttonNo.setOnAction(e -> {
            window.close();
            clicked[0] = false;
        });


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        //window.setMinWidth(220);
        //window.setMaxWidth(220);

        layout.getChildren().addAll(labelExplanation, hBox);
        layout.setAlignment(Pos.CENTER);

        window.setScene(scene);
        window.showAndWait();

        return clicked[0];
    }

}
package bestPackage.scenes;

import bestPackage.GlobalData;
import bestPackage.twPaperErrors.TwPaperErrors;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


class SceneMenu {
    private GlobalData globalData;
    Scene sceneMenu;

    private String user = System.getProperty("user.name");

    private VBox mainVBox = new VBox();
    private VBox VBox2 = new VBox();

    private Button buttonChangeSceneToFixRS = new Button();

    private Button buttonCreatePaperError5FileTW = new Button();
    private Button buttonCloseProgram = new Button();

    private Label labelWelcome = new Label("Siema " + user + "!\nTo wstępna wersja programu, więc jest trochę brzydka. (kot ładny)\nW razie problemów mailuj do Jacka.");

    private Separator separator1 = new Separator();
    private Separator separator2 = new Separator();
    private Separator separator3 = new Separator();
    private Separator separator4 = new Separator();
    private Separator separator5 = new Separator();

    SceneMenu(GlobalData globalData) {
        this.globalData = globalData;
    }

    void loadScene() {
        sceneMenu = new Scene(mainVBox, globalData.getScreenSizeX(), globalData.getScreenSizeY());

        labelWelcome.setWrapText(true);

        ImageView kot = new ImageView(getClass().getResource("/bestPackage/resources/cat.png").toExternalForm());

        buttonChangeSceneToFixRS.setText("RS FIXER");
        ImageView imageRSFix = new ImageView(getClass().getResource("/bestPackage/resources/settings.png").toExternalForm());
        buttonChangeSceneToFixRS.setGraphic(imageRSFix);
        buttonChangeSceneToFixRS.setContentDisplay(ContentDisplay.LEFT);


        buttonCreatePaperError5FileTW.setText("TW Paper error 5");
        ImageView imagestackedpapers = new ImageView(getClass().getResource("/bestPackage/resources/stacked-papers.png").toExternalForm());
        buttonCreatePaperError5FileTW.setGraphic(imagestackedpapers);
        buttonCreatePaperError5FileTW.setContentDisplay(ContentDisplay.LEFT);

        ImageView imageCross = new ImageView(getClass().getResource("/bestPackage/resources/nobw.png").toExternalForm());
        buttonCloseProgram.setGraphic(imageCross);
        buttonCloseProgram.setText("Wyjdź");
        buttonCloseProgram.setContentDisplay(ContentDisplay.LEFT);


        VBox2.setAlignment(Pos.CENTER);
        VBox2.setMinHeight(150);

        separator1.setMinHeight(10);
        separator1.setOpacity(0);
        separator2.setMinHeight(10);
        separator2.setOpacity(0);
        separator3.setMinHeight(10);
        separator3.setOpacity(0);
        separator4.setMinHeight(10);
        separator4.setOpacity(0);
        separator5.setMinHeight(10);
        separator5.setOpacity(0);

        VBox2.getChildren().addAll(kot, buttonChangeSceneToFixRS, separator1,
                buttonCreatePaperError5FileTW, separator5, buttonCloseProgram);

        mainVBox.getChildren().addAll(labelWelcome, VBox2);
    }

    void handleScene(SceneManager sceneManager) {

        buttonChangeSceneToFixRS.setOnAction(e -> {
            sceneManager.setSceneFixRS();

        });

        buttonCreatePaperError5FileTW.setOnAction(e -> {
            TwPaperErrors twPaperErrors = new TwPaperErrors(globalData);
            twPaperErrors.start();
        });

        buttonCloseProgram.setOnAction(e -> {
            sceneManager.primaryStage.close();
        });
    }
}
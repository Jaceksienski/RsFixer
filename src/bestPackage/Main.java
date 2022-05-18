package bestPackage;

import bestPackage.scenes.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage startStage) {

        GlobalData globalData = new GlobalData();

        startStage.getIcons().add(new Image("bestPackage/resources/icon.png"));

        SceneManager sceneManager = new SceneManager(globalData);
        sceneManager.loadSceneFirstTime(startStage, String.valueOf(globalData.getVersion()));
        sceneManager.handleScenes();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

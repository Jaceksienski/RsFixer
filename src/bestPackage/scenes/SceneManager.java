package bestPackage.scenes;

import bestPackage.GlobalData;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    GlobalData globalData;
    Stage primaryStage = new Stage();

    private Scene sceneFixRS;
    private SceneMenu sceneMenuVoid;
    private SceneFixRS sceneFixRSVoid;

    public SceneManager(GlobalData globalData) {
        this.globalData = globalData;
        sceneMenuVoid = new SceneMenu(globalData);
        sceneFixRSVoid = new SceneFixRS(globalData);
    }


    public void loadSceneFirstTime(Stage stage, String version) {
        sceneMenuVoid.loadScene();

//        this.scenePing = scenePingVoid.loadScene();
//        this.sceneDataBaseWrittingCommands = sceneTESTOWASCENAVoid.loadScene();
//        this.sceneDataBaseReadOnly = sceneDataBaseReadOnlyVoid.loadScene();
//        this.sceneCreateConfig = sceneCreateConfigVoid.loadScene();
//        this.sceneConfigureRSGlobal = sceneConfigureRSGlobalVoid.loadScene();
        this.sceneFixRS = sceneFixRSVoid.loadScene();

        primaryStage = stage;
        primaryStage.setTitle("JacaTulsFX v" + version);
        primaryStage.setScene(sceneMenuVoid.sceneMenu);
        primaryStage.show();
    }

    public void handleScenes() {
        sceneMenuVoid.handleScene(this);
//        scenePingVoid.handleScene(this);
//        sceneTESTOWASCENAVoid.handleScene(this);
//        sceneDataBaseReadOnlyVoid.handleScene(this);
//        sceneCreateConfigVoid.handleScene(this);
//        sceneConfigureRSGlobalVoid.handleScene(this);
        sceneFixRSVoid.handleScene(this);
    }

    public void setSceneMenu() {
        primaryStage.setScene(sceneMenuVoid.sceneMenu);
    }

//    void setScenePing() {
//        primaryStage.setScene(scenePing);
//    }
//
//    void setSceneDataBaseWrittingCommands() {
//        primaryStage.setScene(sceneDataBaseWrittingCommands);
//    }
//
//    void setSceneDataBaseReadOnly() {
//        primaryStage.setScene(sceneDataBaseReadOnly);
//    }
//
//    void setSceneCreateConfig() {
//        primaryStage.setScene(sceneCreateConfig);
//    }
//
//    void setSceneConfigureRSGlobal() {
//        primaryStage.setScene(sceneConfigureRSGlobal);
//    }

    void setSceneFixRS() {
        primaryStage.setScene(sceneFixRS);
    }
}

package bestPackage.scenes;

import bestPackage.GlobalData;
import bestPackage.database.RsDataLoaderFromBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SceneFixRS {
    GlobalData globalData = new GlobalData();

    private Scene sceneFixRS;
    private VBox mainVBox = new VBox();
    private HBox hbox = new HBox();
    private ComboBox<String> comboBoxWhatProject = new ComboBox<>();
    private Button buttonChooseWhatProject;

    private Button buttonChangeSceneToMenu = new Button();
    private Label labelExplanation = new Label("Tu możesz naprawić RSa. Fajno nie?");

    //private ScrollPane sp = new ScrollPane();

    private TableView<RsTableData> tableView;

    ObservableList<RsTableData> rsTableData;

    SceneFixRS(GlobalData globalData) {
        this.globalData = globalData;
    }


    public Scene loadScene() {
        sceneFixRS = new Scene(mainVBox, globalData.getScreenSizeX(), globalData.getScreenSizeY());
        labelExplanation.setWrapText(true);
        buttonChangeSceneToMenu.setText("Wróć do Menu");
        buttonChooseWhatProject = new Button("Wybierz");

        comboBoxWhatProject.getItems().addAll("Bydgoszcz", "Gdansk", "Jaworzno", "Lodz");
        comboBoxWhatProject.setPromptText("Wybierz projekt");

        rsTableData = FXCollections.observableArrayList();

        tableView = new TableView<>();
        createTable("");

        hbox.getChildren().addAll(comboBoxWhatProject, buttonChooseWhatProject);
        mainVBox.getChildren().addAll(labelExplanation, hbox, tableView, buttonChangeSceneToMenu);
        return sceneFixRS;
    }

    public void handleScene(SceneManager sceneManager) {
        buttonChangeSceneToMenu.setOnAction(e -> {
            sceneManager.setSceneMenu();
        });

        buttonChooseWhatProject.setOnAction(e -> {
            RsDataLoaderFromBase rsDataLoaderFromBase = new RsDataLoaderFromBase(globalData, rsTableData, comboBoxWhatProject.getValue());

            createTable(comboBoxWhatProject.getValue());
            rsTableData = rsDataLoaderFromBase.refreshRsTableData();
            tableView.setItems(rsTableData);
        });
    }

    void createTable(String project) {
        if (!tableView.getColumns().isEmpty()) {
            tableView.getColumns().clear();
        }

        TableColumn<RsTableData, String> ipColumn = new TableColumn<>("IP");
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));

        TableColumn<RsTableData, Integer> idColumn = new TableColumn<>("Pojazd");
        idColumn.setMaxWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn pingColumn = new TableColumn("Ping");
        pingColumn.setCellValueFactory(new PropertyValueFactory<>("buttPing"));

        TableColumn restartColumn = new TableColumn("Restart");
        restartColumn.setCellValueFactory(new PropertyValueFactory<>("buttRestart"));

        TableColumn kiedyprzyColumn = new TableColumn("Restart");

        if (project.equals("Jaworzno")) {
            kiedyprzyColumn = new TableColumn("KiedyPrzyjedzie");
            kiedyprzyColumn.setCellValueFactory(new PropertyValueFactory<>("buttKiedyPrzyjedzie"));
        }


        tableView.setItems(getRsTableData());

        tableView.getColumns().addAll(ipColumn, idColumn, pingColumn, restartColumn);
        if (project.equals("Jaworzno")) {
            tableView.getColumns().add(kiedyprzyColumn);
        }

        tableView.setMinHeight(globalData.getScreenSizeY() - 70);
    }


    public ObservableList<RsTableData> getRsTableData() {
        return rsTableData;
    }


}

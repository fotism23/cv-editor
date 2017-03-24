package app.java;

import app.java.utils.ApplicationUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EditorScene {
    private Stage mStage;
    private boolean openFile;
    private File file;
    private Parent root;
    private Scene mScene;
    private boolean hasSavePath;

    public EditorScene(boolean template, File file) {
        if (template)
            hasSavePath = false;
        else
            hasSavePath = true;
            this.file = file;
    }

    public Scene initialize(Stage stage) throws Exception {
        this.mStage = stage;
        this.root = FXMLLoader.load(getClass().getResource(ApplicationUtils.EDITOR_WINDOW_LAYOUT_FXML));
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        mStage.setResizable(true);
        mStage.setMinHeight(500);
        mStage.setMinWidth(500);
        mScene = new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        //createCustomListView();
        return mScene;
    }

    private void saveAction() {
        File file = null;
        if (!hasSavePath) {
            file = launchFileChooser();
        }
        if (file != null) {
            this.file = file;
            hasSavePath = true;
            saveFile();
        }
    }

    private File launchFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CV files", ApplicationUtils.APPLICATION_FILE_EXTENSION));
        return fileChooser.showSaveDialog(mStage);
    }

    private void saveFile() {

    }

    private void openFile(){

    }

    /*
    private void createCustomListView() {
        ObservableList<Node> data = FXCollections.observableArrayList();
        data.addAll(new Node("Occupation", true), new Node("Experience", true));

        final ListView<Node> listView = new ListView<Node>(data);
        listView.setCellFactory(new Callback<ListView<Node>, ListCell<Node>>() {
            @Override
            public ListCell<Node> call(ListView<Node> param) {
                return new ListCell<Node>() {
                    @Override
                    protected void updateItem(Node item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            VBox vBox = new VBox(new Text(item.getKey()));
                            HBox hBox = new HBox(new Label("adsfga"));
                        }
                    }
                };
            }
        });
        AnchorPane anchorPane = (AnchorPane) m_scene.lookup("#anchor_pane");
        listView.setLayoutX(50);
        listView.setLayoutY(300);
        listView.setMinWidth(400);
        anchorPane.getChildren().add(listView);
    }
    */
}

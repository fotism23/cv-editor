package app.java;

import app.java.data.DataGenerator;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import app.java.utils.FileChooserUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;

public class EditorScene {
    private Stage mStage;
    private boolean openFile;
    private File file;
    private Parent root;
    private Scene mScene;
    private boolean hasSavePath;
    private boolean saved;
    private DataGenerator dataGenerator;

    public EditorScene(boolean template, File file) {
        if (template){
            this.hasSavePath = false;
            this.saved = false;
            this.file = null;
        } else {
            this.hasSavePath = true;
            this.file = file;
        }
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

    private void openAction() {
        File file = FileChooserUtils.openFileChooser(mStage);
        if (!saved){
            saveAction();
        }
        if (file != null){
            this.file = file;
            saveFile();
        }
    }

    private void saveAction() {
        if (!hasSavePath) {
            saveAsAction();
            return;
        }
        saveFile();
    }

    private void saveAsAction() {
        File file = FileChooserUtils.saveAsFileChooser(mStage);
        if (file != null) {
            this.file = file;
            hasSavePath = true;
            saveFile();
        }
    }

    public void exportAction() {

    }

    private void saveFile() {
        saved = true;
    }

    private void openFile() throws UnsupportedDataTypeException {
        dataGenerator = new DataGenerator(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        showData();
    }


    private void showData() {
        createCustomListView();
    }

    private void createCustomListView() {
        
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

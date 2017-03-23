package app.java;

import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;

public class EditorScene {
    private Stage m_stage;
    private boolean openFile;
    private File file;
    private Parent root;
    private Scene m_scene;

    public EditorScene(boolean openFile, File file) {
        if (openFile){
            if (file != null) {
                this.file = file;
                openFile();
            }
        }

    }

    public Scene initialize(Stage stage) throws Exception {
        this.m_stage = stage;
        this.root = FXMLLoader.load(getClass().getResource(ApplicationUtils.EDITOR_WINDOW_LAYOUT_FXML));
        m_stage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        m_stage.setResizable(true);
        m_stage.setMinHeight(500);
        m_stage.setMinWidth(500);
        m_scene = new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        //createCustomListView();
        return m_scene;
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

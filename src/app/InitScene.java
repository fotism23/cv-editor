package app;

import app.utils.ApplicationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;

public class InitScene {
    @FXML
    private Button createNewButton, openButton, compareButton, aboutButton;


    private Stage m_stage;
    private Scene m_scene;

    private EditorScene editorScene;

    private Popup aboutPopupWindow;

    public Scene initialize(Stage stage) throws Exception{
        this.m_stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource(ApplicationUtils.INIT_WINDOW_LAYOUT_FXML));
        m_stage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        m_stage.setResizable(false);
        m_scene = new Scene(root, ApplicationUtils.INIT_WINDOW_WIDTH, ApplicationUtils.INIT_WINDOW_HEIGHT);
        initializeButtons();


        return m_scene;
    }

    public void setEditorScene(EditorScene editorScene) {
        this.editorScene = editorScene;
    }

    public void initializeButtons() {
        createNewButton = (Button) m_scene.lookup("#createNew");
        openButton = (Button) m_scene.lookup("#open");
        compareButton = (Button) m_scene.lookup("#compare");
        aboutButton = (Button) m_scene.lookup("#about");
        setButtonListeners();
    }


    private void setButtonListeners() {
        createNewButton.setOnAction(event -> launchEditor(null));
        openButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            initializeFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(m_stage);
            launchEditor(file);
        });
        aboutButton.setOnAction(event -> {
            aboutPopupWindow = new Popup();
            aboutPopupWindow.getContent().addAll(new Text("This is a Popup"));
            aboutPopupWindow.show(m_stage);
        });
    }

    private void initializeFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CV files", ApplicationUtils.APPLICATION_FILE_EXTENSION),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    private void launchEditor(File file) {
        if (file != null){
            editorScene = new EditorScene(true, file);
        }else{
            editorScene = new EditorScene(false, null);
        }
        try {
            m_stage.setScene(editorScene.initialize(m_stage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

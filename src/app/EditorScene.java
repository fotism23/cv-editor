package app;

import app.utils.ApplicationUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class EditorScene {
    private Stage m_stage;
    private boolean openFile;
    private File file;

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
        Parent root = FXMLLoader.load(getClass().getResource(ApplicationUtils.EDITOR_WINDOW_LAYOUT_FXML));
        m_stage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        m_stage.setResizable(true);
        return new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);
    }

    private void openFile(){

    }
}

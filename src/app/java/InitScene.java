package app.java;

import app.java.utils.ApplicationUtils;
import app.java.utils.FileChooserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;


public class InitScene {
    @FXML
    private Button createNewButton, openButton, compareButton, aboutButton;


    private Stage mStage;
    private Scene mScene;

    private EditorScene editorScene;

    public Scene initialize(Stage stage) throws Exception{
        this.mStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource(ApplicationUtils.INIT_WINDOW_LAYOUT_FXML));
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        mStage.setResizable(false);
        mScene = new Scene(root, ApplicationUtils.INIT_WINDOW_WIDTH, ApplicationUtils.INIT_WINDOW_HEIGHT);
        initializeButtons();
        return mScene;
    }

    private void initializeButtons() {
        createNewButton = (Button) mScene.lookup("#createNew");
        openButton = (Button) mScene.lookup("#open");
        compareButton = (Button) mScene.lookup("#compare");
        aboutButton = (Button) mScene.lookup("#about");
        setButtonListeners();
    }

    private void setButtonListeners() {
        createNewButton.setOnAction(event -> createNewAction());
        openButton.setOnAction(event -> openAction());
        aboutButton.setOnAction(event -> ApplicationUtils.showAboutDialog()
        );
    }

    private void openAction() {
        File file = FileChooserUtils.openFileChooser(mStage);
        if (file != null) launchEditor(file);
    }

    private void createNewAction() {
        Optional<String> result = ApplicationUtils.showTemplateOptionDialog();

        if (result.isPresent()) {
            if (result.get().equals("Functional CV"))
                launchEditorWithTemplate(new File(ApplicationUtils.FUNCTIONAL_TEMPLATE_PATH));
            else if (result.get().equals("Combined CV"))
                launchEditorWithTemplate(new File(ApplicationUtils.COMBINED_TEMPLATE_PATH));
            else
                launchEditorWithTemplate(new File(ApplicationUtils.CHRONOLOGICAL_TEMPLATE_PATH));
        }
    }

    private void launchEditorWithTemplate(File file) {
        editorScene = new EditorScene(true, file);
        try {
            mStage.setScene(editorScene.initialize(mStage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchEditor(File file) {
        editorScene = new EditorScene(false, file);
        try {
            mStage.setScene(editorScene.initialize(mStage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

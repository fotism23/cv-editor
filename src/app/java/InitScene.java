package app.java;

import app.java.utils.ApplicationUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InitScene {
    @FXML
    private Button createNewButton, openButton, compareButton, aboutButton;


    private Stage mStage;
    private Scene mScene;

    private EditorScene editorScene;

    private Popup aboutPopupWindow;

    public Scene initialize(Stage stage) throws Exception{
        this.mStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource(ApplicationUtils.INIT_WINDOW_LAYOUT_FXML));
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);

        mStage.setResizable(false);
        mScene = new Scene(root, ApplicationUtils.INIT_WINDOW_WIDTH, ApplicationUtils.INIT_WINDOW_HEIGHT);
        initializeButtons();
        mScene.setFill(Color.web("#ffffff"));

        return mScene;
    }

    public void setEditorScene(EditorScene editorScene) {
        this.editorScene = editorScene;
    }

    public void initializeButtons() {
        createNewButton = (Button) mScene.lookup("#createNew");
        openButton = (Button) mScene.lookup("#open");
        compareButton = (Button) mScene.lookup("#compare");
        aboutButton = (Button) mScene.lookup("#about");
        setButtonListeners();
    }

    private void setButtonListeners() {
        createNewButton.setOnAction(event -> showTemplateOptionDialog());
        openButton.setOnAction(event -> {
            File file = getSelectedFile();
            if (file != null) launchEditor(file);
        });
        aboutButton.setOnAction(event -> {
            showAboutDialog();
        });
    }

    private File getSelectedFile() {
        FileChooser fileChooser = new FileChooser();
        initializeFileChooser(fileChooser);
        return fileChooser.showOpenDialog(mStage);
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About");
        alert.setHeaderText("CV Editor v.1.0");
        ImageView img = new ImageView(this.getClass().getResource("../res/drawable/cv_logo.png").toString());

        img.setFitHeight(100);
        img.setFitWidth(100);

        alert.setWidth(200);
        alert.setHeight(200);
        alert.setGraphic(img);
        alert.setContentText("Created By\nFotios Mitropoulos");
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(getClass().getResource("../res/styles/dialog_style.css").toExternalForm());
        pane.getStyleClass().add("myDialog");
        alert.show();
    }

    private void showTemplateOptionDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("Functional CV");
        choices.add("Chronological CV");
        choices.add("Combined CV");

        ImageView img = new ImageView(this.getClass().getResource("../res/drawable/cv_logo.png").toString());
        img.setFitHeight(100);
        img.setFitWidth(100);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Functional CV", choices);
        dialog.setTitle("Template Selection");
        dialog.setHeaderText("Select one of the following choices");
        dialog.setContentText("Template:");
        dialog.setGraphic(img);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("../res/styles/dialog_style.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("myDialog");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (result.get().equals("Functional CV"))
                launchEditorWithTemplate(new File(ApplicationUtils.FUNCTIONAL_TEMPLATE_PATH));
            else if (result.get().equals("Combined CV"))
                launchEditorWithTemplate(new File(ApplicationUtils.COMBINED_TEMPLATE_PATH));
            else
                launchEditorWithTemplate(new File(ApplicationUtils.CHRONOLOGICAL_TEMPLATE_PATH));
        }
    }

    private void initializeFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CV files", ApplicationUtils.APPLICATION_FILE_EXTENSION),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }

    private void launchEditorWithTemplate(File file) {
        editorScene = new EditorScene(true, null);
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

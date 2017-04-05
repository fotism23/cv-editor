package app.java.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public abstract class FileChooserUtils {

    public static File openFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        initializeSelectFileChooser(fileChooser);
        return fileChooser.showOpenDialog(stage);
    }

    public static File saveAsFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        initializeSaveFileChooser(fileChooser);
        return fileChooser.showSaveDialog(stage);
    }

    public static File exportAsFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        initializeExportFileChooser(fileChooser);
        return fileChooser.showSaveDialog(stage);
    }

    public static File openImageFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        initializeOpenImageFileChooser(fileChooser);
        return fileChooser.showOpenDialog(stage);
    }

    private static void initializeOpenImageFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));
    }

    private static void initializeSelectFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CV files", "*" + ApplicationUtils.APPLICATION_FILE_EXTENSION));
    }

    private static void initializeSaveFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CV files", "*" + ApplicationUtils.APPLICATION_FILE_EXTENSION));
    }

    private static void initializeExportFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Export As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Latex file", "*" + ApplicationUtils.LATEX_FILE_EXTENSION),
                new FileChooser.ExtensionFilter("Text File", "*" + ApplicationUtils.TEXT_FILE_EXTENSION));
    }
}

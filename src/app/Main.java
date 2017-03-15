package app;

import app.utils.ApplicationUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("res/layout/main_window.fxml"));
            primaryStage.setTitle("CV Editor");
            primaryStage.setScene(new Scene(root, ApplicationUtils.WINDOW_WIDTH, ApplicationUtils.WINDOW_HEIGHT));
            primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

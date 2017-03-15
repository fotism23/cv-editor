package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static InitScene initScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
            primaryStage.setScene(initScene.initialize(primaryStage));
            primaryStage.show();
    }

    public static void main(String[] args) {
        initScene = new InitScene();

        launch(args);
    }


}

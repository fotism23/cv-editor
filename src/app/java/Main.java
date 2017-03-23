package app.java;

import app.java.utils.ApplicationUtils;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static InitScene initScene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(initScene.initialize(primaryStage));
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(ApplicationUtils.APPLICATION_ICON_PATH)));
        primaryStage.show();
    }

    public static void main(String[] args) {
        initScene = new InitScene();

        launch(args);
    }


}

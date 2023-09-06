package dev.chrismharris.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class AlbumPostrApplication extends Application {

    public static final boolean DEBUG = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                AlbumPostrApplication.class.getResource("/views/intro-view.fxml")
        );
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Album Postr");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        IntroController.executor.shutdownNow();
        System.exit(0);
    }

    public static void main(String[] args) {
        IntroController.clientCredentials_Sync();
        launch(args);
    }
}

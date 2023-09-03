package dev.chrismharris.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PostrEditorApplication {

    public static void start() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                AlbumPostrApplication.class.getResource("/views/editor-view.fxml")
        );
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Postr Editor");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }
}

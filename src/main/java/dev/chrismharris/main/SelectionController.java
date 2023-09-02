package dev.chrismharris.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SelectionController {

    @FXML
    static ImageView selectionImage;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;

    @FXML
    public void initialize() {

    }

    @FXML
    private void yesButtonAction() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        IntroController.continueWithSelectedAlbum();
        stage.close();
    }

    @FXML
    private void noButtonAction() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }

}
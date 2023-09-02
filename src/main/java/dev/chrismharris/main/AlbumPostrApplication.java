package dev.chrismharris.main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class AlbumPostrApplication extends Application {



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

    public static void main(String[] args) {
        IntroController.clientCredentials_Sync();
        launch(args);
    }
}

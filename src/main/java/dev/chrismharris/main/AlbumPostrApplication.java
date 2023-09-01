package dev.chrismharris.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(Keys.CLIENT_ID)
            .setClientSecret(Keys.CLIENT_SECRET)
            .build();

    public static final ClientCredentialsRequest clientCredientialsRequest = spotifyApi.clientCredentials()
            .build();

    public static final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums("Post Malone")
            .build();

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

        final ClientCredentials clientCredentials = clientCredientialsRequest.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        System.out.println("Expires in " + clientCredentials.getExpiresIn());

        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();

            System.out.println("Total: " + albumSimplifiedPaging.getTotal());
            AlbumSimplified[] albums = albumSimplifiedPaging.getItems();
            for (AlbumSimplified a : albums) {
                System.out.println(a.getName());
                System.out.println("\t" + a.getReleaseDate());
                System.out.println("\t" + a.getId() + "\n\tBy: ");
                for (ArtistSimplified artist : a.getArtists()) {
                    System.out.println("\t\t" + artist.getName());
                    System.out.println("\t\t\t" + artist.getId());
                    System.out.println("\t\t\t" + artist.getHref());
                    System.out.println("\t\t\t" + artist.getUri());
                }
            }
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error when searching for album: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
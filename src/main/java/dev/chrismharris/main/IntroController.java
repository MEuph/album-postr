package dev.chrismharris.main;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.table.AlbumCellKeyHandler;
import dev.chrismharris.table.AlbumCellMouseHandler;
import dev.chrismharris.table.StringTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

import java.io.IOException;
import java.util.Objects;

public class IntroController {

    @FXML
    Button searchButton;

    @FXML
    TextField searchQuery;

    @FXML
    TableView<PostrAlbum> albumTableView = new TableView<>();
    public static ObservableList<PostrAlbum> albumList = FXCollections.observableArrayList();

    private static final String clientId = Keys.CLIENT_ID;
    private static final String clientSecret = Keys.CLIENT_SECRET;

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static PostrAlbum currentlySelected;

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchForAlbums(String query) {
        final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query)
                .limit(10)
                .build();

        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();

            AlbumSimplified[] albums = albumSimplifiedPaging.getItems();
            if (albums.length > 0) {
                albumList.clear();
            }
            for (AlbumSimplified a : albums) {
                albumList.add(new PostrAlbum(a.getName(), a.getArtists(), a.getReleaseDate(), a.getImages()[0], a.getId()));
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error when searching for album: " + "'" + query + "'" + e.getMessage());
        }

    }

    @FXML
    public void initialize() {
        albumTableView.setEditable(false);
        Callback<TableColumn<PostrAlbum, String>, TableCell<PostrAlbum, String>> stringCellFactory =
                param -> {
                    StringTableCell cell = new StringTableCell();
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new AlbumCellMouseHandler());
                    cell.addEventHandler(KeyEvent.KEY_TYPED, new AlbumCellKeyHandler());
                    return cell;
                };

        TableColumn<PostrAlbum, String> colName = new TableColumn<>("Album Name");
        colName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        colName.setCellFactory(stringCellFactory);

        TableColumn<PostrAlbum, String> colArtists = new TableColumn<>("Artists");
        colArtists.setCellValueFactory(
                new PropertyValueFactory<>("artists"));
        colArtists.setCellFactory(stringCellFactory);

        TableColumn<PostrAlbum, String> colDate = new TableColumn<>("Release Date");
        colDate.setCellValueFactory(
                new PropertyValueFactory<PostrAlbum, String>("releaseDate"));
        colDate.setCellFactory(stringCellFactory);

        TableColumn<PostrAlbum, ImageView> colImg = new TableColumn<>("Album Art");
        colImg.setCellValueFactory(new PropertyValueFactory<>("albumArt"));

        albumTableView.setItems(albumList);
        albumTableView.getColumns().addAll(colImg, colName, colArtists, colDate);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchForAlbums(searchQuery.getText());
            }
        });

        searchQuery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchButton.fire();
            }
        });
    }

    public static void promptForContinuation(PostrAlbum a) {
        IntroController.currentlySelected = a;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to use this album?", ButtonType.YES, ButtonType.NO);
        ImageView imageView = new ImageView(new Image(a.getAlbumArt().getImage().getUrl(), 100, 100, true, true));
        alert.setGraphic(imageView);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Objects.requireNonNull(AlbumPostrApplication.class.getResource("/stylesheet/ap_style.css")).toExternalForm()
        );
        dialogPane.getStyleClass().add("selectionDialog");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            continueWithSelectedAlbum();
        }
    }

    public static void continueWithSelectedAlbum() {
        // TODO: Move this to its own Application class and auto-populate fields in the application
        // TODO: Generate image, then open new window to edit and save generated image
        try {
            PostrEditorApplication.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
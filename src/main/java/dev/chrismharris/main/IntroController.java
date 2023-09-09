package dev.chrismharris.main;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.table.AlbumCellKeyHandler;
import dev.chrismharris.table.AlbumCellMouseHandler;
import dev.chrismharris.table.ImageTableCell;
import dev.chrismharris.table.StringTableCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.*;

public class IntroController {

    @FXML
    Label infoLabel;

    @FXML
    Button searchButton;

    @FXML
    TextField searchQuery;

    @FXML
    TableView<PostrAlbum> albumTableView = new TableView<>();

    public static final int MAX_ALBUMS_TO_LOAD = 50;

    public static ObservableList<PostrAlbum> albumList = FXCollections.observableArrayList();

    private static final String clientId = Keys.CLIENT_ID;
    private static final String clientSecret = Keys.CLIENT_SECRET;

    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static PostrAlbum currentlySelected;

    private boolean loading = false;

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

    public void searchForAlbums(String query) throws InterruptedException {
        if (loading) return;

        final SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query)
                .limit(MAX_ALBUMS_TO_LOAD)
                .build();

        try {
            // Takes a little over a second
            ArrayList<AlbumSimplified> albums = new ArrayList<>();
            infoLabel.setText("Fetching Albums from Spotify...");
            executor.execute(() -> {
                try {
                    Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
                    Platform.runLater(() -> {
                        albums.addAll(Arrays.asList(albumSimplifiedPaging.getItems()));

                        infoLabel.setText("Checking if table needs to be cleared...");

                        // Clear what was previously table only if we get any results
                        if (!albumList.isEmpty() && !albums.isEmpty()) {
                            albumList.clear();
                        }

                        infoLabel.setText("Loading albums...");
                        // Takes over a second for each album to load, so we'll do it concurrently
                        for (AlbumSimplified a : albums) {
                            executor.execute(() -> {
                                PostrAlbum p = new PostrAlbum(a.getName(), a.getArtists(), a.getReleaseDate(), a.getImages()[0], a.getId());
                                Platform.runLater(() -> {
                                    loading = true;
                                    infoLabel.setText("Loaded " + p.getName() + "...");
                                    albumList.add(p);
                                    if (albumList.size() >= MAX_ALBUMS_TO_LOAD) {
                                        loading = false;
                                        infoLabel.setText("Finished loading!");
                                    }
                                });
                            });
                        }
                    });
                } catch (IOException | SpotifyWebApiException | ParseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error when searching for album: " + "'" + query + "'\n" + e.getMessage(), ButtonType.OK);
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(
                            Objects.requireNonNull(AlbumPostrApplication.class.getResource("/stylesheet/ap_style.css")).toExternalForm()
                    );
                    dialogPane.getStyleClass().add("selectionDialog");
                    alert.showAndWait();
                }
            });
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error when searching for album: " + "'" + query + "'\n" + e.getMessage(), ButtonType.OK);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    Objects.requireNonNull(AlbumPostrApplication.class.getResource("/stylesheet/ap_style.css")).toExternalForm()
            );
            dialogPane.getStyleClass().add("selectionDialog");
            alert.showAndWait();
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
        Callback<TableColumn<PostrAlbum, Object>, TableCell<PostrAlbum, Object>> objectCellFactory =
                param -> {
                    ImageTableCell cell = new ImageTableCell();
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
                new PropertyValueFactory<>("releaseDate"));
        colDate.setCellFactory(stringCellFactory);

        TableColumn<PostrAlbum, ImageView> colImg = new TableColumn<>("Album Art");
        colImg.setCellValueFactory(new PropertyValueFactory<>("albumArt"));

        albumTableView.setItems(albumList);
        colImg.setPrefWidth(600.0/4.0);
        colName.setPrefWidth(600.0/4.0);
        colArtists.setPrefWidth(600.0/4.0);
        colDate.setPrefWidth(600.0/4.0);
        albumTableView.getColumns().addAll(colImg, colName, colArtists, colDate);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    searchForAlbums(searchQuery.getText());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
        ImageView imageView = new ImageView(a.getAlbumArtThumbnail());
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
        try {
            PostrEditorApplication.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
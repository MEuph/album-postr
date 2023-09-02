package dev.chrismharris.main;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.table.StringCellEventHandler;
import dev.chrismharris.table.StringTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

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

    public static final Paging<AlbumSimplified> searchForAlbums(String query) {
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
                albumList.add(new PostrAlbum(a.getName(), a.getArtists(), a.getReleaseDate(), a.getImages()[0]));

//                System.out.println(a.getName());
//                System.out.println("\t" + a.getReleaseDate());
//                System.out.println("\t" + a.getId() + "\n\tBy: ");
//                for (ArtistSimplified artist : a.getArtists()) {
//                    System.out.println("\t\t" + artist.getName());
//                    System.out.println("\t\t\t" + artist.getId());
//                    System.out.println("\t\t\t" + artist.getHref());
//                    System.out.println("\t\t\t" + artist.getUri());
//                }
            }

            return albumSimplifiedPaging;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error when searching for album: " + "\'" + query + "\'" + e.getMessage());
        }

        return null;
    }

    @FXML
    public void initialize() {
        albumTableView.setEditable(false);
        Callback<TableColumn, TableCell> stringCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        StringTableCell cell = new StringTableCell();
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new StringCellEventHandler());
                        return cell;
                    }
                };



        TableColumn colName = new TableColumn("Album Name");
        colName.setCellValueFactory(
                new PropertyValueFactory<PostrAlbum, String>("name"));
        colName.setCellFactory(stringCellFactory);

        TableColumn colArtists = new TableColumn("Artists");
        colArtists.setCellValueFactory(
                new PropertyValueFactory<PostrAlbum, String>("artists"));
        colArtists.setCellFactory(stringCellFactory);

        TableColumn colDate = new TableColumn("Release Date");
        colDate.setCellValueFactory(
                new PropertyValueFactory<PostrAlbum, String>("releaseDate"));
        colDate.setCellFactory(stringCellFactory);

        TableColumn<PostrAlbum, ImageView> colImg = new TableColumn<PostrAlbum, ImageView>("Album Art");
        colImg.setCellValueFactory(new PropertyValueFactory<PostrAlbum, ImageView>("albumArt"));

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
}
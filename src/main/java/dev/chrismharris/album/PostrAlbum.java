package dev.chrismharris.album;

import dev.chrismharris.main.IntroController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;

public class PostrAlbum {

    private final SimpleStringProperty name;
    private final SimpleStringProperty artists;
    private final SimpleStringProperty releaseDate;
    private final SimpleObjectProperty<ImageView> albumArt;
    private String albumArtUrl;
    private final ArrayList<PostrTrack> tracks;
    private String mainArtist;

    public PostrAlbum(String name, ArtistSimplified[] artists, String releaseDate, Image art, String id) {
        this.tracks = new ArrayList<PostrTrack>();
        this.name = new SimpleStringProperty(name);
        StringJoiner joiner = new StringJoiner(", ");
        for (ArtistSimplified a : artists) {
            joiner.add(a.getName());
        }
        this.mainArtist = artists[0].getName(); // it is assumed that the main artist will the first in the list
        this.artists = new SimpleStringProperty(joiner.toString());
        this.releaseDate = new SimpleStringProperty(releaseDate);
        this.albumArtUrl = art.getUrl();
        ImageView unprocessed = new ImageView(new javafx.scene.image.Image(albumArtUrl, 48, 48, true, false));
        this.albumArt = new SimpleObjectProperty<ImageView>(unprocessed);
        System.out.println("url: " + art.getUrl());

        try {
            final GetAlbumsTracksRequest req = IntroController.spotifyApi.getAlbumsTracks(id).build();
            final Paging<TrackSimplified> trackSimplifiedPaging = req.execute();

            for (TrackSimplified t : trackSimplifiedPaging.getItems()) {
                this.tracks.add(new PostrTrack(t));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getArtists() {
        return artists.get();
    }

    public String getMainArtist() {
        return this.mainArtist;
    }

    public void setArtists(ArtistSimplified[] s) {
        StringBuilder aut = new StringBuilder();
        for (ArtistSimplified artist : s) {
            aut.append(artist.getName());
        }
        this.artists.set(aut.toString());
    }

    public String getReleaseDate() {
        return releaseDate.get();
    }

    public void setReleaseDate(String s) {
        releaseDate.set(s);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String s) {
        name.set(s);
    }

    public ImageView getAlbumArt() {
        return albumArt.get();
    }

    public void setAlbumArt(Image art) {
        this.albumArtUrl = art.getUrl();
        this.albumArt.set(new ImageView(new javafx.scene.image.Image(albumArtUrl)));
    }

    public String getAlbumArtUrl() {
        return albumArtUrl;
    }

    public ArrayList<PostrTrack> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        return getName() +
                "\n\tArtists: " +
                "\n\t\t" +
                getArtists() +
                "\n\t" +
                getReleaseDate() +
                "\n\t" +
                getAlbumArt().getImage().getUrl();
    }
}

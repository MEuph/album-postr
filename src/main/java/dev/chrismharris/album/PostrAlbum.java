package dev.chrismharris.album;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;

import java.util.StringJoiner;

public class PostrAlbum {

    private final SimpleStringProperty name;
    private final SimpleStringProperty artists;
    private final SimpleStringProperty releaseDate;
    private final SimpleObjectProperty<ImageView> albumArt;

    public PostrAlbum(String name, ArtistSimplified[] artists, String releaseDate, Image art) {
        this.name = new SimpleStringProperty(name);
        StringJoiner joiner = new StringJoiner(", ");
        for (ArtistSimplified a : artists) {
            joiner.add(a.getName());
        }
        this.artists = new SimpleStringProperty(joiner.toString());
        this.releaseDate = new SimpleStringProperty(releaseDate);
        ImageView unprocessed = new ImageView(new javafx.scene.image.Image(art.getUrl(), 48, 48, true, false));
        this.albumArt = new SimpleObjectProperty<ImageView>(unprocessed);
        System.out.println("url: " + art.getUrl());
    }

    public String getArtists() {
        return artists.get();
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
        this.albumArt.set(new ImageView(new javafx.scene.image.Image(art.getUrl())));
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getName())
                .append("\n\tArtists: ")
                .append("\n\t\t")
                .append(getArtists())
                .append("\n\t")
                .append(getReleaseDate())
                .append("\n\t")
                .append(getAlbumArt().getImage().getUrl()).toString();
    }
}

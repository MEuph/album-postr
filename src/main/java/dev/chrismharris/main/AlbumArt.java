package dev.chrismharris.main;

import javafx.scene.image.ImageView;

public class AlbumArt {

    private ImageView image;

    public AlbumArt(ImageView img) {
        this.image = img;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }
}

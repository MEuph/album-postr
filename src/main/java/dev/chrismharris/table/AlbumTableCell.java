package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import javafx.scene.control.TableCell;

public class AlbumTableCell extends TableCell<PostrAlbum, String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString());
        setGraphic(null);
    }
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}
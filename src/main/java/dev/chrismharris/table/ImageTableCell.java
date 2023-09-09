package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import javafx.scene.control.TableCell;

public class ImageTableCell extends TableCell<PostrAlbum, Object> {

    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString().toString());
        setGraphic(null);
    }
    private Object getString() {
        return getItem() == null ? "" : getItem();
    }

}
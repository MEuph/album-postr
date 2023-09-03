package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import javafx.scene.control.TableCell;

public class IntegerTableCell extends TableCell<PostrAlbum, Integer> {

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString());
        setGraphic(null);
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}
package dev.chrismharris.main;

import javafx.scene.control.TableCell;

public class StringTableCell extends TableCell<PostrAlbum, String> {

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
package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import javafx.scene.control.TableCell;

public class TimeTableCell extends TableCell<PostrAlbum, Integer> {

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : getString());
        setGraphic(null);
    }

    private String getString() {
        if (getItem() == null) return "";

        int min = (getItem() / 1000) / 60;
        int sec = (getItem() / 1000) % 60;

        return min + ":" + String.format("%02d", sec);
    }

}
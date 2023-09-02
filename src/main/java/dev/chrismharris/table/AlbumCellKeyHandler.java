package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.main.IntroController;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AlbumCellKeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            TableCell c = (TableCell) event.getSource();
            int index = c.getIndex();
            try {
                PostrAlbum album = IntroController.albumList.get(index);
                IntroController.promptForContinuation(album);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}
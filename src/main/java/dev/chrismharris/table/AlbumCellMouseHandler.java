package dev.chrismharris.table;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.main.IntroController;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

public class AlbumCellMouseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            TableCell c = (TableCell) event.getSource();
            int index = c.getIndex();
            try {
                PostrAlbum album = IntroController.albumList.get(index);
                if (event.getClickCount() == 2) {
                    IntroController.promptForContinuation(album);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
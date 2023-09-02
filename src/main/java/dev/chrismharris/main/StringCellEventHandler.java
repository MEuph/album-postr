package dev.chrismharris.main;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

public class StringCellEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            TableCell c = (TableCell)event.getSource();
            int index = c.getIndex();

            try {
                PostrAlbum album = IntroController.albumList.get(index);
                System.out.println("Name = " + album.getName());
                System.out.println("Authors = " + album.getArtists().toString());
                System.out.println("Release Date = " + album.getReleaseDate());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
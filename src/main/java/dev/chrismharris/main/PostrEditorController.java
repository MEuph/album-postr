package dev.chrismharris.main;

import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ;
import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.album.PostrTrack;
import dev.chrismharris.table.IntegerTableCell;
import dev.chrismharris.table.StringTableCell;
import dev.chrismharris.table.TimeTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class PostrEditorController {

    @FXML
    public ColorPicker postrBackgroundColor;
    @FXML
    public ColorPicker paletteColor1;
    @FXML
    public ColorPicker paletteColor2;
    @FXML
    public ColorPicker paletteColor3;
    @FXML
    public ColorPicker paletteColor4;
    @FXML
    public ColorPicker paletteColor5;
    @FXML
    public TextField postrTitleField;
    @FXML
    public DatePicker postrReleaseDateField;
    @FXML
    public TextField postrMainArtistField;
    @FXML
    public Button addSongButton;
    @FXML
    public Button deleteSongButton;
    @FXML
    public TableView<PostrTrack> songTableView;
    @FXML
    public Button addSigFromFileButton;
    @FXML
    public Canvas previewCanvas;
    @FXML
    public Button savePostrButton;
    @FXML
    public Button fullSizePreviewButton;

    public static ObservableList<PostrTrack> trackList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadFromAlbum(IntroController.currentlySelected);
    }

    public void loadFromAlbum(PostrAlbum a) {
        loadColorPickers(a);
        loadTextFields(a);
        loadSongs(a);

        generateImage(a);
    }

    private void loadColorPickers(PostrAlbum a) {
        // get palette from image using ColorThief
        BufferedImage img = SwingFXUtils.fromFXImage(a.getAlbumArt().getImage(), null);
        MMCQ.CMap result = ColorThief.getColorMap(img, 5);

        loadFromPalette(postrBackgroundColor, new int[][]{
                {232, 228, 214} // off-white
        }, 0);

        // populate color pickers
        loadFromPalette(paletteColor1, result.palette(), 0);
        loadFromPalette(paletteColor2, result.palette(), 1);
        loadFromPalette(paletteColor3, result.palette(), 2);
        loadFromPalette(paletteColor4, result.palette(), 3);
        loadFromPalette(paletteColor5, result.palette(), 4);
    }

    private void loadFromPalette(ColorPicker picker, int[][] palette, int index) {
        picker.setValue(new Color((double)(palette[index][0]) / 255.0,
                (double)(palette[index][1]) / 255.0,
                (double)(palette[index][2]) / 255.0,
                1.0));
    }

    private void loadTextFields(PostrAlbum a) {
        // title
        postrTitleField.setText(a.getName());
        // release date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(a.getReleaseDate(), formatter);
        postrReleaseDateField.setValue(localDate);
        // main artist
        postrMainArtistField.setText(a.getMainArtist());
    }

    private void loadSongs(PostrAlbum a) {
        trackList.clear();

        ArrayList<PostrTrack> tracks = a.getTracks();
        trackList.addAll(tracks);

        songTableView.setEditable(false);
        Callback<TableColumn, TableCell> stringCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        StringTableCell cell = new StringTableCell();
                        // add listeners here
                        return cell;
                    }
                };

        Callback<TableColumn, TableCell> integerCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        IntegerTableCell cell = new IntegerTableCell();
                        // add listeners here
                        return cell;
                    }
                };

        Callback<TableColumn, TableCell> timeCellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        TimeTableCell cell = new TimeTableCell();
                        // add listeners here
                        return cell;
                    }
                };

        TableColumn trackNumberCol = new TableColumn("Track #");
        trackNumberCol.setCellValueFactory(
                new PropertyValueFactory<PostrTrack, Integer>("trackNumber")
        );
        trackNumberCol.setCellFactory(integerCellFactory);

        TableColumn trackNameCol = new TableColumn("Name");
        trackNameCol.setCellValueFactory(
                new PropertyValueFactory<PostrTrack, String>("name"));
        trackNameCol.setCellFactory(stringCellFactory);

        TableColumn trackDurationCol = new TableColumn("Duration");
        trackDurationCol.setCellValueFactory(
                new PropertyValueFactory<PostrTrack, String>("durationMs"));
        trackDurationCol.setCellFactory(timeCellFactory);

        songTableView.setItems(trackList);
        songTableView.getColumns().addAll(trackNumberCol, trackNameCol, trackDurationCol);
    }

    private void generateImage(PostrAlbum a) {
        BufferedImage img = new BufferedImage(1200, 1600, BufferedImage.TYPE_INT_RGB);
        Image art = new Image(a.getAlbumArtUrl(), 990, 990, true, true);
        BufferedImage albumArt = SwingFXUtils.fromFXImage(art, null);
        Graphics2D g = (Graphics2D)img.getGraphics();

        // -- FILL BACKGROUND --
        g.setColor(fxToSwingColor(postrBackgroundColor.getValue()));
        g.fillRect(0, 0, 1200, 1600);

        // -- DRAW ALBUM ART --
        g.drawImage(albumArt, 105, 212, 990, 990, java.awt.Color.WHITE, null);

        // -- DRAW TEXT FIELDS --
        g.setFont(new Font(
               "Montserrat",
                Font.BOLD,
                75
        ));
        g.setColor(java.awt.Color.BLACK);
        g.setStroke(new BasicStroke(10f));
        g.drawLine(105, 50, 990, 50);
        g.drawString(postrTitleField.getText(), 105, 140);

        g.setFont(new Font(
                "Montserrat",
                Font.BOLD,
                40
        ));
        g.drawString(Integer.toString(postrReleaseDateField.getValue().getYear()), 1025, 75);

        g.fillRect(105, (212+990), (int)(990.0*0.6), 75);
        g.setColor(java.awt.Color.WHITE);
        g.drawString(postrMainArtistField.getText(), 110, (212+990)+10+40);

        // -- DRAW COLOR PALETTE --
        g.setColor(fxToSwingColor(paletteColor1.getValue()));
        g.fillRect(105+(int)(990*0.6), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
        g.setColor(fxToSwingColor(paletteColor2.getValue()));
        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 1), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
        g.setColor(fxToSwingColor(paletteColor3.getValue()));
        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 2), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
        g.setColor(fxToSwingColor(paletteColor4.getValue()));
        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 3), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
        g.setColor(fxToSwingColor(paletteColor5.getValue()));
        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 4), (212+990), (int)(990.0*0.4*0.2 * 1), 75);

        // -- DRAW SONGS
        // draw in columns of 7
        int i = 0;
        int v = 0;
        for (int j = 0; j < trackList.size(); j++) {
            if (j % 7 == 0 && j != 0) {
                i++;
                v = 0;
            }
            g.setColor(java.awt.Color.BLACK);
            g.setFont(new Font(
                    "Montserrat",
                    Font.PLAIN,
                    25
            ));
            g.drawString(trackList.get(j).getTrackNumber() +
                    ". " + trackList.get(j).getName(), 105 + (i * 350), (212 + 990 + 75 + 35 + (v * 40)));
            v++;
        }

        g.setColor(java.awt.Color.BLACK);
        g.setStroke(new BasicStroke(10f));
        g.drawLine(105, 1580, 1200-105, 1580);


        g.dispose();

        previewCanvas.getGraphicsContext2D().drawImage(
                SwingFXUtils.toFXImage(img, null), 0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
    }

    private java.awt.Color fxToSwingColor(javafx.scene.paint.Color c) {
        int r = (int)(c.getRed() * 255.0);
        int g = (int)(c.getGreen() * 255.0);
        int b = (int)(c.getBlue() * 255.0);

        return new java.awt.Color(r, g, b);
    }
}
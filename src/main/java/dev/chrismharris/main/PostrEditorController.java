package dev.chrismharris.main;

import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.album.PostrTrack;
import dev.chrismharris.table.IntegerTableCell;
import dev.chrismharris.table.StringTableCell;
import dev.chrismharris.table.TimeTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public static ObservableList<PostrTrack> trackList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadFromAlbum(IntroController.currentlySelected);
    }

    public void loadFromAlbum(PostrAlbum a) {
        loadColorPickers();
        loadTextFields(a);
        loadSongs(a);
    }

    private void loadColorPickers() {

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
}
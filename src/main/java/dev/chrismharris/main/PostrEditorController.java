package dev.chrismharris.main;

import dev.chrismharris.album.PostrTrack;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PostrEditorController {

    @FXML
    public ColorPicker postrBackgroundColor;
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
    @FXML
    public HBox postrBackgroundColorHBox;
    @FXML
    public HBox postrTitleFieldHBox;
    @FXML
    public HBox postrReleaseDateFieldHBox;
    @FXML
    public HBox postrDateFormatFieldHBox;
    @FXML
    public TextField postrDateFormatField;
    @FXML
    public HBox postrMainArtistFieldHBox;
    @FXML
    public TitledPane advancedSettingsTitledPane;
    @FXML
    public VBox advancedSettingsVBox;
    @FXML
    public TitledPane albumArtSettingsTitledPane;
    @FXML
    public VBox albumArtSettingsVBox;
    @FXML
    public CheckBox albumArtSquareCheckBox;
    @FXML
    public Spinner<Integer> albumArtWidthSpinner;
    @FXML
    public Spinner<Integer> albumArtHeightSpinner;
    @FXML
    public TextField albumArtUrlTextField;
    @FXML
    public TitledPane paletteSettingsTitledPane;
    @FXML
    public VBox paletteSettingsVBox;
    @FXML
    public Spinner<Integer> paletteColorsNumberSpinner;
    @FXML
    public Spinner<Integer> paletteWidthSpinner;
    @FXML
    public Spinner<Integer> paletteHeightSpinner;
    @FXML
    public TitledPane placementSettingsTitledPane;
    @FXML
    public VBox placementSettingsVBox;
    @FXML
    public TitledPane tracksPlacementSettingsTitledPane;
    @FXML
    public VBox tracksPlacementSettingsVBox;
    @FXML
    public Spinner<Integer> numTracksPerColumnSpinner;
    @FXML
    public Spinner<Integer> tracksVerticalSpacingSpinner;
    @FXML
    public Spinner<Integer> tracksHorizontalSpacingSpinner;
    @FXML
    public Spinner<Integer> tracksNewlineThresholdSpinner;
    @FXML
    public TitledPane palettePlacementSettingsTitledPane;
    @FXML
    public VBox palettePlacementSettingsVBox;
    @FXML
    public Spinner<Integer> paletteHorizontalSpacingSpinner;
    @FXML
    public Spinner<Integer> paletteDistanceFromArtistSpinner;
    @FXML
    public TitledPane releaseDatePlacementSettingsTitledPane;
    @FXML
    public VBox releaseDatePlacementSettingsVBox;
    @FXML
    public Spinner<Integer> releaseDateXPositionSpinner;
    @FXML
    public Spinner<Integer> releaseDateYPositionSpinner;
    @FXML
    public TitledPane titlePlacementSettingsTitledPane;
    @FXML
    public VBox titlePlacementSettingsVBox;
    @FXML
    public Spinner<Integer> titleXPositionSpinner;
    @FXML
    public Spinner<Integer> titleYPositionSpinner;
    @FXML
    public TitledPane artistPlacementSettingsTitledPane;
    @FXML
    public VBox artistPlacementSettingsVBox;
    @FXML
    public Spinner<Integer> artistXPositionSpinner;
    @FXML
    public Spinner<Integer> artistVerticalSpacingSpinner;
    @FXML
    public TitledPane albumArtPlacementSettingsTitledPane;
    @FXML
    public VBox albumArtPlacementSettingsVBox;
    @FXML
    public Spinner<Integer> albumArtXPositionSpinner;
    @FXML
    public Spinner<Integer> albumArtYPositionSpinner;
    @FXML
    public TitledPane colorAndFontSettingsTitledPane;
    @FXML
    public VBox colorAndFontSettingsVBox;
    @FXML
    public TitledPane fontSettingsTitledPane;
    @FXML
    public VBox fontSettingsVBox;
    @FXML
    public TextField globalFontFamilyTextField;
    @FXML
    public CheckBox useGlobalFontFamilyCheckBox;
    @FXML
    public ColorPicker globalFontColorPicker;
    @FXML
    public CheckBox useGlobalFontColorCheckBox;
    @FXML
    public TitledPane tracksColorAndFontSettingsTitledPane;
    @FXML
    public VBox tracksColorAndFontSettingsVBox;
    @FXML
    public TextField tracksFontFamilyTextField;
    @FXML
    public CheckBox tracksAlternateColorsCheckBox;
    @FXML
    public ColorPicker tracksColor1Picker;
    @FXML
    public ColorPicker tracksColor2Picker;
    @FXML
    public CheckBox highlightExplicitTracksCheckBox;
    @FXML
    public ColorPicker explicitTracksHighlightColorPicker;
    @FXML
    public TitledPane paletteColorAndFontSettingsTitledPane;
    @FXML
    public VBox paletteColorAndFontSettingsVBox;
    @FXML
    public ColorPicker paletteColor1ColorPicker;
    @FXML
    public ColorPicker paletteColor2ColorPicker;
    @FXML
    public ColorPicker paletteColor3ColorPicker;
    @FXML
    public ColorPicker paletteColor4ColorPicker;
    @FXML
    public ColorPicker paletteColor5ColorPicker;
    @FXML
    public ColorPicker paletteColor6ColorPicker;
    @FXML
    public ColorPicker paletteColor7ColorPicker;
    @FXML
    public ColorPicker paletteColor8ColorPicker;
    @FXML
    public ColorPicker paletteColor9ColorPicker;
    @FXML
    public ColorPicker paletteColor10ColorPicker;
    @FXML
    public TitledPane releaseDateColorAndFontSettingsTitledPane;
    @FXML
    public VBox releaseDateColorAndFontSettingsVBox;
    @FXML
    public TextField releaseDateFontFamilyTextField;
    @FXML
    public ColorPicker releaseDateFontColorPicker;
    @FXML
    public ColorPicker releaseDateFontBackgroundColor;
    @FXML
    public Spinner<Integer> releaseDateBackgroundHorizontalMarginsSpinner;
    @FXML
    public Spinner<Integer> releaseDateBackgroundVerticalMarginsSpinner;
    @FXML
    public TitledPane titleColorAndFontSettingsTitledPane;
    @FXML
    public VBox titleColorAndFontSettingsVBox;
    @FXML
    public TextField titleFontFamilyTextField;
    @FXML
    public ColorPicker titleFontColorPicker;
    @FXML
    public ColorPicker titleBackgroundColorPicker;
    @FXML
    public Spinner<Integer> titleBackgroundHorizontalMarginsSpinner;
    @FXML
    public Spinner<Integer> titleBackgroundVerticalMarginsSpinner;
    @FXML
    public TitledPane signatureSettingsTitledPane;
    @FXML
    public VBox signatureSettingsVBox;
    @FXML
    public Spinner<Integer> signatureXPositionSpinner;
    @FXML
    public Spinner<Integer> signatureYPositionSpinner;
    @FXML
    public Spinner<Integer> signatureWidthSpinner;
    @FXML
    public Spinner<Integer> signatureHeightSpinner;
    @FXML
    public CheckBox signaturePreserveRatioCheckBox;
    @SuppressWarnings("unused")
    public static ObservableList<PostrTrack> trackList = FXCollections.observableArrayList();

    ChangeListener<Object> listener = ((observable, oldValue, newValue)
            -> System.out.println("[BASIC LISTENER]: " + observable.toString() + " changed from "
            + (oldValue == null ? "null" : oldValue.toString()) + " to " + newValue.toString() + "!"));

    @FXML
    public void initialize() {
        giveListenerToAllVariables();
//        loadFromAlbum(IntroController.currentlySelected);
    }

    public void giveListenerToAllVariables() {
        postrBackgroundColor.valueProperty().addListener(listener);
        postrTitleField.textProperty().addListener(listener);
        postrReleaseDateField.valueProperty().addListener(listener);
        postrMainArtistField.textProperty().addListener(listener);
        addSongButton.setOnAction(event -> System.out.println("[BUTTON LISTENER]: Add Song Button Pressed"));
        deleteSongButton.setOnAction(event -> System.out.println("[BUTTON LISTENER]: Delete Song Button Pressed"));
        songTableView.itemsProperty().addListener(listener);
        addSigFromFileButton.setOnAction(event -> System.out.println("[BUTTON LISTENER]: Add Signature From File Button Pressed"));
        savePostrButton.setOnAction(event -> System.out.println("[BUTTON LISTENER]: Save Postr Button Pressed"));
        fullSizePreviewButton.setOnAction(event -> System.out.println("[BUTTON LISTENER]: Full Size Preview Button Pressed"));
        postrDateFormatField.textProperty().addListener(listener);
        albumArtSquareCheckBox.selectedProperty().addListener(listener);
        albumArtWidthSpinner.valueProperty().addListener(listener);
        albumArtHeightSpinner.valueProperty().addListener(listener);
        albumArtUrlTextField.textProperty().addListener(listener);
        paletteColorsNumberSpinner.valueProperty().addListener(listener);
        paletteWidthSpinner.valueProperty().addListener(listener);
        paletteHeightSpinner.valueProperty().addListener(listener);
        numTracksPerColumnSpinner.valueProperty().addListener(listener);
        tracksVerticalSpacingSpinner.valueProperty().addListener(listener);
        tracksHorizontalSpacingSpinner.valueProperty().addListener(listener);
        tracksNewlineThresholdSpinner.valueProperty().addListener(listener);
        paletteHorizontalSpacingSpinner.valueProperty().addListener(listener);
        paletteDistanceFromArtistSpinner.valueProperty().addListener(listener);
        releaseDateXPositionSpinner.valueProperty().addListener(listener);
        releaseDateYPositionSpinner.valueProperty().addListener(listener);
        titleXPositionSpinner.valueProperty().addListener(listener);
        titleYPositionSpinner.valueProperty().addListener(listener);
        artistXPositionSpinner.valueProperty().addListener(listener);
        artistVerticalSpacingSpinner.valueProperty().addListener(listener);
        albumArtXPositionSpinner.valueProperty().addListener(listener);
        albumArtYPositionSpinner.valueProperty().addListener(listener);
        globalFontFamilyTextField.textProperty().addListener(listener);
        useGlobalFontFamilyCheckBox.selectedProperty().addListener(listener);
        globalFontColorPicker.valueProperty().addListener(listener);
        useGlobalFontColorCheckBox.selectedProperty().addListener(listener);
        tracksFontFamilyTextField.textProperty().addListener(listener);
        tracksAlternateColorsCheckBox.selectedProperty().addListener(listener);
        tracksColor1Picker.valueProperty().addListener(listener);
        tracksColor2Picker.valueProperty().addListener(listener);
        highlightExplicitTracksCheckBox.selectedProperty().addListener(listener);
        explicitTracksHighlightColorPicker.valueProperty().addListener(listener);
        paletteColor1ColorPicker.valueProperty().addListener(listener);
        paletteColor2ColorPicker.valueProperty().addListener(listener);
        paletteColor3ColorPicker.valueProperty().addListener(listener);
        paletteColor4ColorPicker.valueProperty().addListener(listener);
        paletteColor5ColorPicker.valueProperty().addListener(listener);
        paletteColor6ColorPicker.valueProperty().addListener(listener);
        paletteColor7ColorPicker.valueProperty().addListener(listener);
        paletteColor8ColorPicker.valueProperty().addListener(listener);
        paletteColor9ColorPicker.valueProperty().addListener(listener);
        paletteColor10ColorPicker.valueProperty().addListener(listener);
        releaseDateFontFamilyTextField.textProperty().addListener(listener);
        releaseDateFontColorPicker.valueProperty().addListener(listener);
        releaseDateFontBackgroundColor.valueProperty().addListener(listener);
        releaseDateBackgroundHorizontalMarginsSpinner.valueProperty().addListener(listener);
        releaseDateBackgroundVerticalMarginsSpinner.valueProperty().addListener(listener);
        titleFontFamilyTextField.textProperty().addListener(listener);
        titleFontColorPicker.valueProperty().addListener(listener);
        titleBackgroundColorPicker.valueProperty().addListener(listener);
        titleBackgroundHorizontalMarginsSpinner.valueProperty().addListener(listener);
        titleBackgroundVerticalMarginsSpinner.valueProperty().addListener(listener);
        signatureXPositionSpinner.valueProperty().addListener(listener);
        signatureYPositionSpinner.valueProperty().addListener(listener);
        signatureWidthSpinner.valueProperty().addListener(listener);
        signatureHeightSpinner.valueProperty().addListener(listener);
        signaturePreserveRatioCheckBox.selectedProperty().addListener(listener);
    }

//    public void loadFromAlbum(PostrAlbum a) {
//        loadColorPickers(a);
//        loadTextFields(a);
//        loadSongs(a);
//
//        generateImage(a);
//    }
//
//    private void loadColorPickers(PostrAlbum a) {
//        // get palette from image using ColorThief
//        BufferedImage img = SwingFXUtils.fromFXImage(a.getAlbumArt().getImage(), null);
//        MMCQ.CMap result = ColorThief.getColorMap(img, 5);
//
//        loadFromPalette(postrBackgroundColor, new int[][]{
//                {232, 228, 214} // off-white
//        }, 0);
//
//        // populate color pickers
//        loadFromPalette(paletteColor1, result.palette(), 0);
//        loadFromPalette(paletteColor2, result.palette(), 1);
//        loadFromPalette(paletteColor3, result.palette(), 2);
//        loadFromPalette(paletteColor4, result.palette(), 3);
//        loadFromPalette(paletteColor5, result.palette(), 4);
//    }
//
//    private void loadFromPalette(ColorPicker picker, int[][] palette, int index) {
//        picker.setValue(new Color((double)(palette[index][0]) / 255.0,
//                (double)(palette[index][1]) / 255.0,
//                (double)(palette[index][2]) / 255.0,
//                1.0));
//    }
//
//    private void loadTextFields(PostrAlbum a) {
//        // title
//        postrTitleField.setText(a.getName());
//        // release date
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate localDate = LocalDate.parse(a.getReleaseDate(), formatter);
//        postrReleaseDateField.setValue(localDate);
//        // main artist
//        postrMainArtistField.setText(a.getMainArtist());
//    }
//
//    private void loadSongs(PostrAlbum a) {
//        trackList.clear();
//
//        ArrayList<PostrTrack> tracks = a.getTracks();
//        trackList.addAll(tracks);
//
//        songTableView.setEditable(false);
//        Callback<TableColumn, TableCell> stringCellFactory =
//                new Callback<TableColumn, TableCell>() {
//                    @Override
//                    public TableCell call(TableColumn param) {
//                        StringTableCell cell = new StringTableCell();
//                        // add listeners here
//                        return cell;
//                    }
//                };
//
//        Callback<TableColumn, TableCell> integerCellFactory =
//                new Callback<TableColumn, TableCell>() {
//                    @Override
//                    public TableCell call(TableColumn param) {
//                        IntegerTableCell cell = new IntegerTableCell();
//                        // add listeners here
//                        return cell;
//                    }
//                };
//
//        Callback<TableColumn, TableCell> timeCellFactory =
//                new Callback<TableColumn, TableCell>() {
//                    @Override
//                    public TableCell call(TableColumn param) {
//                        TimeTableCell cell = new TimeTableCell();
//                        // add listeners here
//                        return cell;
//                    }
//                };
//
//        TableColumn trackNumberCol = new TableColumn("Track #");
//        trackNumberCol.setCellValueFactory(
//                new PropertyValueFactory<PostrTrack, Integer>("trackNumber")
//        );
//        trackNumberCol.setCellFactory(integerCellFactory);
//
//        TableColumn trackNameCol = new TableColumn("Name");
//        trackNameCol.setCellValueFactory(
//                new PropertyValueFactory<PostrTrack, String>("name"));
//        trackNameCol.setCellFactory(stringCellFactory);
//
//        TableColumn trackDurationCol = new TableColumn("Duration");
//        trackDurationCol.setCellValueFactory(
//                new PropertyValueFactory<PostrTrack, String>("durationMs"));
//        trackDurationCol.setCellFactory(timeCellFactory);
//
//        songTableView.setItems(trackList);
//        songTableView.getColumns().addAll(trackNumberCol, trackNameCol, trackDurationCol);
//    }
//
//    private void generateImage(PostrAlbum a) {
//        BufferedImage img = new BufferedImage(1200, 1600, BufferedImage.TYPE_INT_RGB);
//        Image art = new Image(a.getAlbumArtUrl(), 990, 990, true, true);
//        BufferedImage albumArt = SwingFXUtils.fromFXImage(art, null);
//        Graphics2D g = (Graphics2D)img.getGraphics();
//
//        // -- FILL BACKGROUND --
//        g.setColor(fxToSwingColor(postrBackgroundColor.getValue()));
//        g.fillRect(0, 0, 1200, 1600);
//
//        // -- DRAW ALBUM ART --
//        g.drawImage(albumArt, 105, 212, 990, 990, java.awt.Color.WHITE, null);
//
//        // -- DRAW TEXT FIELDS --
//        g.setFont(new Font(
//               "Montserrat",
//                Font.BOLD,
//                75
//        ));
//        g.setColor(java.awt.Color.BLACK);
//        g.setStroke(new BasicStroke(10f));
//        g.drawLine(105, 50, 990, 50);
//        g.drawString(postrTitleField.getText(), 105, 140);
//
//        g.setFont(new Font(
//                "Montserrat",
//                Font.BOLD,
//                40
//        ));
//        g.drawString(Integer.toString(postrReleaseDateField.getValue().getYear()), 1025, 75);
//
//        g.fillRect(105, (212+990), (int)(990.0*0.6), 75);
//        g.setColor(java.awt.Color.WHITE);
//        g.drawString(postrMainArtistField.getText(), 110, (212+990)+10+40);
//
//        // -- DRAW COLOR PALETTE --
//        g.setColor(fxToSwingColor(paletteColor1.getValue()));
//        g.fillRect(105+(int)(990*0.6), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
//        g.setColor(fxToSwingColor(paletteColor2.getValue()));
//        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 1), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
//        g.setColor(fxToSwingColor(paletteColor3.getValue()));
//        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 2), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
//        g.setColor(fxToSwingColor(paletteColor4.getValue()));
//        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 3), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
//        g.setColor(fxToSwingColor(paletteColor5.getValue()));
//        g.fillRect(105+(int)(990*0.6) + (int)(990.0*0.4*0.2 * 4), (212+990), (int)(990.0*0.4*0.2 * 1), 75);
//
//        // -- DRAW SONGS
//        // draw in columns of 7
//        int i = 0;
//        int v = 0;
//        for (int j = 0; j < trackList.size(); j++) {
//            if (j % 7 == 0 && j != 0) {
//                i++;
//                v = 0;
//            }
//            g.setColor(java.awt.Color.BLACK);
//            g.setFont(new Font(
//                    "Montserrat",
//                    Font.PLAIN,
//                    25
//            ));
//            g.drawString(trackList.get(j).getTrackNumber() +
//                    ". " + trackList.get(j).getName(), 105 + (i * 350), (212 + 990 + 75 + 35 + (v * 40)));
//            v++;
//        }
//
//        g.setColor(java.awt.Color.BLACK);
//        g.setStroke(new BasicStroke(10f));
//        g.drawLine(105, 1580, 1200-105, 1580);
//
//
//        g.dispose();
//
//        previewCanvas.getGraphicsContext2D().drawImage(
//                SwingFXUtils.toFXImage(img, null), 0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
//    }
//
//    private java.awt.Color fxToSwingColor(javafx.scene.paint.Color c) {
//        int r = (int)(c.getRed() * 255.0);
//        int g = (int)(c.getGreen() * 255.0);
//        int b = (int)(c.getBlue() * 255.0);
//
//        return new java.awt.Color(r, g, b);
//    }
}
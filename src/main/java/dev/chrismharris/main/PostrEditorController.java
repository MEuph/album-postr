package dev.chrismharris.main;

import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ;
import dev.chrismharris.album.PostrAlbum;
import dev.chrismharris.album.PostrTrack;
import dev.chrismharris.table.IntegerTableCell;
import dev.chrismharris.table.StringTableCell;
import dev.chrismharris.table.TimeTableCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Add track editing
// TODO: Implement full size preview button
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
    public Button addTrackButton;
    @FXML
    public Button deleteTrackButton;
    @FXML
    public TableView<PostrTrack> trackTableView;
    @FXML
    public Button loadSignatureButton;
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
    public CheckBox preserveRatioCheckBox;
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
    public ComboBox<String> globalFontFamilyComboBox;
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
    public ComboBox<String> tracksFontFamilyComboBox;
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
    public ComboBox<String> releaseDateFontFamilyComboBox;
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
    public ComboBox<String> titleFontFamilyComboBox;
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
    @FXML
    public Button regeneratePostrButton;
    @FXML
    public SplitPane mainSplitPane;

    @FXML
    public ColorPicker postrArtFillColor;
    @FXML
    public TitledPane artistColorAndFontSettingsTitledPane;
    @FXML
    public VBox artistColorAndFontSettingsVBox;
    @FXML
    public ComboBox<String> artistFontFamilyComboBox;
    @FXML
    public ColorPicker artistFontColorPicker;
    @FXML
    public ColorPicker artistBackgroundColorPicker;
    @FXML
    public Spinner<Integer> artistBackgroundHorizontalMarginsSpinner;
    @FXML
    public Spinner<Integer> artistBackgroundVerticalMarginsSpinner;
    @FXML
    public HBox enableTopLineCheckBoxHBox;
    @FXML
    public CheckBox enableTopLineCheckBox;
    @FXML
    public HBox enableBottomLineCheckBoxHBox;
    @FXML
    public CheckBox enableBottomLineCheckBox;
    @FXML
    public Spinner<Integer> globalFontSizeSpinner;
    @FXML
    public CheckBox useGlobalFontSizeCheckBox;
    @FXML
    public Spinner<Integer> tracksFontSizeSpinner;
    @FXML
    public Spinner<Integer> releaseDateFontSizeSpinner;
    @FXML
    public Spinner<Integer> titleFontSizeSpinner;
    @FXML
    public Spinner<Integer> artistFontSizeSpinner;
    @FXML
    public ComboBox<String> globalFontWeightComboBox;
    @FXML
    public ComboBox<String> titleFontWeightComboBox;
    @FXML
    public ComboBox<String> releaseDateFontWeightComboBox;
    @FXML
    public ComboBox<String> tracksFontWeightComboBox;
    @FXML
    public ComboBox<String> artistFontWeightComboBox;
    @FXML
    public CheckBox useGlobalFontWeightCheckBox;
    @FXML
    public CheckBox darkToLightCheckBox;
    @FXML
    public HBox enablePaletteCheckBoxHBox;
    @FXML
    public CheckBox enablePaletteCheckBox;
    @FXML
    public Spinner<Integer> paletteXOffsetSpinner;
    @FXML
    public Spinner<Integer> paletteYOffsetSpinner;
    @FXML
    public Spinner<Integer> topLineX1PositionSpinner;
    @FXML
    public Spinner<Integer> topLineY1PositionSpinner;
    @FXML
    public Spinner<Integer> topLineX2PositionSpinner;
    @FXML
    public Spinner<Integer> topLineY2PositionSpinner;
    @FXML
    public Spinner<Integer> topLineThicknessSpinner;
    @FXML
    public ColorPicker topLineColorPicker;
    @FXML
    public Spinner<Integer> bottomLineX1PositionSpinner;
    @FXML
    public Spinner<Integer> bottomLineY1PositionSpinner;
    @FXML
    public Spinner<Integer> bottomLineX2PositionSpinner;
    @FXML
    public Spinner<Integer> bottomLineY2PositionSpinner;
    @FXML
    public Spinner<Integer> bottomLineThicknessSpinner;
    @FXML
    public ColorPicker bottomLineColorPicker;

    public static final ExecutorService executor = Executors.newFixedThreadPool(1);

    public static ObservableList<PostrTrack> trackList = FXCollections.observableArrayList();

    public boolean colorPickersLoaded = false;
    public boolean textFieldsLoaded = false;
    public boolean tracksLoaded = false;
    public boolean comboBoxesLoaded = false;
    public boolean isLoading = false;
    public boolean signatureLoaded = false;
    public String errorMessage = "";

    public ObservableList<String> availableFonts;

    public ArrayList<Spinner<Integer>> integerSpinners = new ArrayList<>();

    public File signatureFile;

    public BufferedImage finalPostrImage;

    @FXML
    public void initialize() {
        String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        availableFonts = FXCollections.observableArrayList(
                availableFontFamilyNames
        );

        globalFontFamilyComboBox.setItems(availableFonts);
        titleFontFamilyComboBox.setItems(availableFonts);
        tracksFontFamilyComboBox.setItems(availableFonts);
        releaseDateFontFamilyComboBox.setItems(availableFonts);
        artistFontFamilyComboBox.setItems(availableFonts);

        loadSpinnersList();

        darkToLightCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loadPaletteColorPickers(IntroController.currentlySelected);
            generateImage();
        });

        for (Spinner<Integer> i : integerSpinners) {
            i.setEditable(true);
        }

        regeneratePostrButton.setOnAction(event -> {
            if (isLoading) return;
            isLoading = true;
            executor.execute(() -> Platform.runLater(this::generateImage));
        });

        loadSignatureButton.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Signature Image");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("PNG (Recommended)", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg")
            );

            signatureFile = fileChooser.showOpenDialog(loadSignatureButton.getScene().getWindow());
            if (signatureFile != null) {
                try {
                    BufferedImage signature = ImageIO.read(signatureFile);
                    signatureWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1200, signature.getWidth()));
                    signatureHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1600, signature.getHeight()));
                    signatureLoaded = true;
                    generateImage();
                } catch (IOException e) {
                    addError("Could not load signature file + \"" + signatureFile.getPath() + "\"");
                    signatureLoaded = false;
                }
            }
        });

        savePostrButton.setOnAction(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Postr");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG (Recommended)", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("All Images", "*.*")
            );
            File file = fileChooser.showSaveDialog(loadSignatureButton.getScene().getWindow());
            if (file != null) {
                try {
                    ImageIO.write(finalPostrImage, file.getName().substring(file.getName().length() - 3), file);
                } catch (IOException e) {
                    addError("Could not save image!\n" + e.getMessage());
                }
            }
        });

        loadFromAlbum(IntroController.currentlySelected);
    }

    public void loadComboBoxes() {
        ObservableList<String> weightOptions = FXCollections.observableArrayList(
                "Plain",
                "Bold",
                "Italic",
                "ItalicBold"
        );

        globalFontWeightComboBox.setItems(weightOptions);
        titleFontWeightComboBox.setItems(weightOptions);
        releaseDateFontWeightComboBox.setItems(weightOptions);
        tracksFontWeightComboBox.setItems(weightOptions);
        artistFontWeightComboBox.setItems(weightOptions);

        globalFontWeightComboBox.setValue(weightOptions.get(0));
        titleFontWeightComboBox.setValue(weightOptions.get(0));
        releaseDateFontWeightComboBox.setValue(weightOptions.get(0));
        tracksFontWeightComboBox.setValue(weightOptions.get(0));
        artistFontWeightComboBox.setValue(weightOptions.get(0));

        comboBoxesLoaded = true;
    }

    public void loadSpinnersList() {
        integerSpinners.add(albumArtWidthSpinner);
        integerSpinners.add(albumArtHeightSpinner);
        integerSpinners.add(paletteColorsNumberSpinner);
        integerSpinners.add(paletteWidthSpinner);
        integerSpinners.add(paletteHeightSpinner);
        integerSpinners.add(numTracksPerColumnSpinner);
        integerSpinners.add(tracksVerticalSpacingSpinner);
        integerSpinners.add(tracksHorizontalSpacingSpinner);
        integerSpinners.add(tracksNewlineThresholdSpinner);
        integerSpinners.add(paletteHorizontalSpacingSpinner);
        integerSpinners.add(paletteDistanceFromArtistSpinner);
        integerSpinners.add(releaseDateXPositionSpinner);
        integerSpinners.add(releaseDateYPositionSpinner);
        integerSpinners.add(titleXPositionSpinner);
        integerSpinners.add(titleYPositionSpinner);
        integerSpinners.add(artistXPositionSpinner);
        integerSpinners.add(artistVerticalSpacingSpinner);
        integerSpinners.add(albumArtXPositionSpinner);
        integerSpinners.add(albumArtYPositionSpinner);
        integerSpinners.add(releaseDateBackgroundHorizontalMarginsSpinner);
        integerSpinners.add(releaseDateBackgroundVerticalMarginsSpinner);
        integerSpinners.add(titleBackgroundHorizontalMarginsSpinner);
        integerSpinners.add(titleBackgroundVerticalMarginsSpinner);
        integerSpinners.add(signatureXPositionSpinner);
        integerSpinners.add(signatureYPositionSpinner);
        integerSpinners.add(signatureWidthSpinner);
        integerSpinners.add(signatureHeightSpinner);
        integerSpinners.add(artistBackgroundHorizontalMarginsSpinner);
        integerSpinners.add(artistBackgroundVerticalMarginsSpinner);
        integerSpinners.add(globalFontSizeSpinner);
        integerSpinners.add(titleFontSizeSpinner);
        integerSpinners.add(releaseDateFontSizeSpinner);
        integerSpinners.add(tracksFontSizeSpinner);
        integerSpinners.add(artistFontSizeSpinner);
        integerSpinners.add(paletteXOffsetSpinner);
        integerSpinners.add(paletteYOffsetSpinner);
        integerSpinners.add(topLineX1PositionSpinner);
        integerSpinners.add(topLineY1PositionSpinner);
        integerSpinners.add(topLineX2PositionSpinner);
        integerSpinners.add(topLineY2PositionSpinner);
        integerSpinners.add(topLineThicknessSpinner);
        integerSpinners.add(bottomLineX1PositionSpinner);
        integerSpinners.add(bottomLineY1PositionSpinner);
        integerSpinners.add(bottomLineX2PositionSpinner);
        integerSpinners.add(bottomLineY2PositionSpinner);
        integerSpinners.add(bottomLineThicknessSpinner);
    }

    public void loadFromAlbum(PostrAlbum a) {
        loadColorPickers(a);
        loadTextFields(a);
        loadTracks(a);
        loadComboBoxes();

        isLoading = true;
        executor.execute(() -> Platform.runLater(this::generateImage));
    }

    public static HashMap<Integer, Double> sortPaletteByLuminance(HashMap<Integer, Double> unsorted, boolean darkToLight) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list =
                new LinkedList<>(unsorted.entrySet());

        // Sort the list
        list.sort((o1, o2) -> (darkToLight ? 1 : -1) * (o1.getValue()).compareTo(o2.getValue()));

        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private void loadColorPickers(PostrAlbum a) {
        loadFromPalette(postrBackgroundColor, new int[][]{
                {232, 228, 214} // off-white
        }, 0);

        loadPaletteColorPickers(a);

        titleFontColorPicker.setValue(javafx.scene.paint.Color.BLACK);
        releaseDateFontColorPicker.setValue(javafx.scene.paint.Color.BLACK);
        tracksColor1Picker.setValue(javafx.scene.paint.Color.BLACK);
        tracksColor2Picker.setValue(javafx.scene.paint.Color.BLACK);
        artistFontColorPicker.setValue(javafx.scene.paint.Color.WHITE);
        artistBackgroundColorPicker.setValue(javafx.scene.paint.Color.BLACK);

        postrArtFillColor.setValue(javafx.scene.paint.Color.WHITE);

        topLineColorPicker.setValue(javafx.scene.paint.Color.BLACK);
        bottomLineColorPicker.setValue(javafx.scene.paint.Color.BLACK);

        releaseDateFontBackgroundColor.setValue(new javafx.scene.paint.Color(0, 0, 0, 0));
        titleBackgroundColorPicker.setValue(new javafx.scene.paint.Color(0, 0, 0, 0));

        colorPickersLoaded = true;
    }

    private void loadPaletteColorPickers(PostrAlbum a) {
        // get palette from image using ColorThief
        BufferedImage img = SwingFXUtils.fromFXImage(a.getAlbumArt().getImage(), null);
        MMCQ.CMap result = ColorThief.getColorMap(img, paletteColorsNumberSpinner.getValue());

        int[][] unsortedPalette = result.palette();
        HashMap<Integer, Double> unsortedPaletteMap = new HashMap<>();

        // sort palette by brightness in decreasing order
        for (int i = 0; i < unsortedPalette.length; i++) {
            unsortedPaletteMap.put(i, Math.sqrt(
                    0.299 * Math.pow(unsortedPalette[i][0] / 255.0, 2) +
                            0.587 * Math.pow(unsortedPalette[i][1] / 255.0, 2) +
                            0.114 * Math.pow(unsortedPalette[i][2] / 255.0, 2)
            ));
        }

        Map<Integer, Double> palette = sortPaletteByLuminance(unsortedPaletteMap, darkToLightCheckBox.isSelected());

        ColorPicker[] pickers = new ColorPicker[]{
                paletteColor1ColorPicker,
                paletteColor2ColorPicker,
                paletteColor3ColorPicker,
                paletteColor4ColorPicker,
                paletteColor5ColorPicker,
                paletteColor6ColorPicker,
                paletteColor7ColorPicker,
                paletteColor8ColorPicker,
                paletteColor9ColorPicker,
                paletteColor10ColorPicker,
        };

        // populate color pickers
        int i = 0;
        for (Map.Entry<Integer, Double> en : palette.entrySet()) {
            loadFromPalette(pickers[i], result.palette(), en.getKey());
            i++;
        }
        for (int j = i; j < 10; j++) {
            pickers[j].setValue(new javafx.scene.paint.Color(0, 0, 0, 1));
        }
    }

    private void loadFromPalette(ColorPicker picker, int[][] palette, int index) {
        picker.setValue(new javafx.scene.paint.Color((double) (palette[index][0]) / 255.0,
                (double) (palette[index][1]) / 255.0,
                (double) (palette[index][2]) / 255.0,
                1.0));
    }

    private void loadTextFields(PostrAlbum a) {
        // title
        postrTitleField.setText(a.getName());
        // release date
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(a.getReleaseDate(), formatter);
            postrReleaseDateField.setValue(localDate);
        } catch (Exception e) {
            addError("Release date could not be loaded. You must set it manually");
            showErrorMessage();
        }
        // main artist
        postrMainArtistField.setText(a.getMainArtist());
        // image url
        albumArtUrlTextField.setText(a.getAlbumArtUrl());
        // fonts
        titleFontFamilyComboBox.setValue("Montserrat");
        tracksFontFamilyComboBox.setValue("Montserrat");
        releaseDateFontFamilyComboBox.setValue("Montserrat");
        artistFontFamilyComboBox.setValue("Montserrat");

        textFieldsLoaded = true;
    }

    @SuppressWarnings("all")
    private void loadTracks(PostrAlbum a) {
        ArrayList<PostrTrack> tracks = a.getTracks();
        trackList.setAll(tracks);

        trackTableView.setEditable(false);
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

        trackTableView.setItems(trackList);
        trackTableView.getColumns().clear();
        trackTableView.getColumns().addAll(trackNumberCol, trackNameCol, trackDurationCol);
        trackTableView.setMinHeight(150);

        tracksLoaded = true;
    }

    private void generateImage() {
        previewCanvas.getGraphicsContext2D().clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
        BufferedImage img = new BufferedImage(1200, 1600, BufferedImage.TYPE_INT_RGB);
        Image art = new Image(albumArtUrlTextField.getText(), albumArtWidthSpinner.getValue(), albumArtHeightSpinner.getValue(), preserveRatioCheckBox.isSelected(), true);
        BufferedImage albumArt = SwingFXUtils.fromFXImage(art, null);
        Graphics2D g = (Graphics2D) img.getGraphics();

        // -- FILL BACKGROUND --
        g.setColor(fxToSwingColor(postrBackgroundColor.getValue()));
        g.fillRect(0, 0, 1200, 1600);

        // -- DRAW ALBUM ART --
        g.drawImage(albumArt, albumArtXPositionSpinner.getValue(),
                albumArtYPositionSpinner.getValue(),
                albumArtWidthSpinner.getValue(),
                albumArtHeightSpinner.getValue(),
                fxToSwingColor(postrArtFillColor.getValue()), null);

        // -- LOAD FONTS
        Font globalFont, titleFont, releaseDateFont, tracksFont, artistFont;
        java.awt.Color globalFontColor, titleFontColor, releaseDateFontColor, tracksFontColor1, tracksFontColor2, artistFontColor;

        globalFont = titleFont = releaseDateFont = tracksFont = artistFont = null;
        globalFontColor = titleFontColor = releaseDateFontColor = tracksFontColor1 = tracksFontColor2 = artistFontColor = null;

        if (useGlobalFontFamilyCheckBox.isSelected()) {
            if (useGlobalFontSizeCheckBox.isSelected()) {
                globalFont = validateFont(globalFontFamilyComboBox,
                        useGlobalFontWeightCheckBox.isSelected() ? parseWeight(globalFontWeightComboBox.getValue()) :
                                Font.PLAIN, globalFontSizeSpinner.getValue());
            } else {
                globalFont = validateFont(globalFontFamilyComboBox,
                        useGlobalFontWeightCheckBox.isSelected() ? parseWeight(globalFontWeightComboBox.getValue()) :
                                Font.PLAIN, 45);
            }

            g.setFont(globalFont);
        } else {
            titleFont = validateFont(titleFontFamilyComboBox, parseWeight(titleFontWeightComboBox.getValue()), titleFontSizeSpinner.getValue());
            releaseDateFont = validateFont(releaseDateFontFamilyComboBox, parseWeight(releaseDateFontWeightComboBox.getValue()), releaseDateFontSizeSpinner.getValue());
            tracksFont = validateFont(tracksFontFamilyComboBox, parseWeight(tracksFontWeightComboBox.getValue()), tracksFontSizeSpinner.getValue());
            artistFont = validateFont(artistFontFamilyComboBox, parseWeight(artistFontWeightComboBox.getValue()), artistFontSizeSpinner.getValue());
        }

        if (useGlobalFontColorCheckBox.isSelected()) {
            globalFontColor = fxToSwingColor(globalFontColorPicker.getValue());
        } else {
            titleFontColor = fxToSwingColor(titleFontColorPicker.getValue());
            releaseDateFontColor = fxToSwingColor(releaseDateFontColorPicker.getValue());
            tracksFontColor1 = fxToSwingColor(tracksColor1Picker.getValue());
            tracksFontColor2 = fxToSwingColor(tracksColor2Picker.getValue());
            artistFontColor = fxToSwingColor(artistFontColorPicker.getValue());
        }

        // -- DRAW ALBUM TITLE --
        if (titleBackgroundColorPicker.getValue().getOpacity() != 0) {
            AffineTransform at = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(at, true, true);

            g.setColor(fxToSwingColor(titleBackgroundColorPicker.getValue()));
            Font toUse = useGlobalFontFamilyCheckBox.isSelected() ? globalFont : titleFont;
            if (toUse == null) toUse = new Font("Arial", Font.PLAIN, 20);

            int titleBackgroundWidth = (int) (toUse.getStringBounds(postrTitleField.getText(), frc).getWidth()) + titleBackgroundHorizontalMarginsSpinner.getValue();
            int titleBackgroundHeight = (int) (toUse.getStringBounds(postrTitleField.getText(), frc).getHeight()) + titleBackgroundVerticalMarginsSpinner.getValue();

            g.fillRect(
                    titleXPositionSpinner.getValue(),
                    titleYPositionSpinner.getValue() - titleBackgroundHeight,
                    titleBackgroundWidth,
                    titleBackgroundHeight
            );
        }

        g.setFont(useGlobalFontFamilyCheckBox.isSelected() ? globalFont : titleFont);
        g.setColor(useGlobalFontColorCheckBox.isSelected() ? globalFontColor : titleFontColor);
        g.drawString(postrTitleField.getText(), titleXPositionSpinner.getValue(), titleYPositionSpinner.getValue());

        // -- DRAW RELEASE DATE --
        try { // if the formatting is successful
            AffineTransform at = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(at, true, true);
            String formattedReleaseDate = postrReleaseDateField.getValue().format(
                    DateTimeFormatter.ofPattern(postrDateFormatField.getText()));

            g.setFont(useGlobalFontFamilyCheckBox.isSelected() ? globalFont : releaseDateFont);
            if (releaseDateFontBackgroundColor.getValue().getOpacity() != 0) {
                g.setColor(fxToSwingColor(releaseDateFontBackgroundColor.getValue()));

                Font toUse = useGlobalFontFamilyCheckBox.isSelected() ? globalFont : releaseDateFont;
                if (toUse == null) toUse = new Font("Arial", Font.PLAIN, 20);

                int releaseDateBackgroundWidth = (int) (toUse.getStringBounds(formattedReleaseDate, frc).getWidth()) + releaseDateBackgroundHorizontalMarginsSpinner.getValue();
                int releaseDateBackgroundHeight = (int) (toUse.getStringBounds(formattedReleaseDate, frc).getHeight()) + releaseDateBackgroundVerticalMarginsSpinner.getValue();

                g.fillRect(
                        releaseDateXPositionSpinner.getValue(),
                        releaseDateYPositionSpinner.getValue() - releaseDateBackgroundHeight,
                        releaseDateBackgroundWidth,
                        releaseDateBackgroundHeight
                );
            }

            g.setColor(useGlobalFontColorCheckBox.isSelected() ? globalFontColor : releaseDateFontColor);
            g.drawString(formattedReleaseDate,
                    releaseDateXPositionSpinner.getValue() + (releaseDateBackgroundHorizontalMarginsSpinner.getValue() / 2),
                    releaseDateYPositionSpinner.getValue() - (releaseDateBackgroundVerticalMarginsSpinner.getValue() / 2));
        } catch (Exception e) { // if formatting is unsuccessful
            AffineTransform at = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(at, true, true);

            g.setFont(useGlobalFontFamilyCheckBox.isSelected() ? globalFont : releaseDateFont);
            if (releaseDateFontBackgroundColor.getValue().getOpacity() != 0) {
                g.setColor(fxToSwingColor(releaseDateFontBackgroundColor.getValue()));

                Font toUse = useGlobalFontFamilyCheckBox.isSelected() ? globalFont : releaseDateFont;
                if (toUse == null) toUse = new Font("Arial", Font.PLAIN, 20);

                int rdWidth = (int) (toUse.getStringBounds("!ERROR!", frc).getWidth());
                int rdHeight = (int) (toUse.getStringBounds("!ERROR!", frc).getHeight());
                g.fillRect(
                        releaseDateXPositionSpinner.getValue(),
                        releaseDateYPositionSpinner.getValue(),
                        rdWidth + releaseDateBackgroundHorizontalMarginsSpinner.getValue(),
                        rdHeight + releaseDateBackgroundVerticalMarginsSpinner.getValue());
            }

            g.setColor(Color.RED);
            g.drawString("!ERROR!",
                    releaseDateXPositionSpinner.getValue() + (releaseDateBackgroundHorizontalMarginsSpinner.getValue() / 2),
                    releaseDateYPositionSpinner.getValue() + (releaseDateBackgroundVerticalMarginsSpinner.getValue() / 2));
            addError("Release Date Format is Incorrect. Try yyyy-MM-dd");
        }

        // TODO: Make a button to align the right edge of the palette with the right edge of the album art

        // -- DRAW MAIN ARTIST NAME
        g.setColor(fxToSwingColor(artistBackgroundColorPicker.getValue()));

        g.fillRect(artistXPositionSpinner.getValue(), (albumArtYPositionSpinner.getValue() + albumArtHeightSpinner.getValue()
                        + artistVerticalSpacingSpinner.getValue()),
                (int) (((artistBackgroundHorizontalMarginsSpinner.getValue() * 2) + albumArtWidthSpinner.getValue()) * 0.6),
                paletteHeightSpinner.getValue());

        g.setFont(useGlobalFontFamilyCheckBox.isSelected() ? globalFont : artistFont);

        g.setColor(useGlobalFontColorCheckBox.isSelected() ? globalFontColor : artistFontColor);

        g.drawString(postrMainArtistField.getText(), artistXPositionSpinner.getValue()
                + artistBackgroundHorizontalMarginsSpinner.getValue(), (albumArtYPositionSpinner.getValue()
                + albumArtHeightSpinner.getValue() + artistVerticalSpacingSpinner.getValue()
                + artistFontSizeSpinner.getValue() + artistBackgroundVerticalMarginsSpinner.getValue()));


        // -- DRAW COLOR PALETTE --

        if (enablePaletteCheckBox.isSelected()) {
            ArrayList<ColorPicker> palettePickers = new ArrayList<>();
            palettePickers.add(paletteColor1ColorPicker);
            palettePickers.add(paletteColor2ColorPicker);
            palettePickers.add(paletteColor3ColorPicker);
            palettePickers.add(paletteColor4ColorPicker);
            palettePickers.add(paletteColor5ColorPicker);
            palettePickers.add(paletteColor6ColorPicker);
            palettePickers.add(paletteColor7ColorPicker);
            palettePickers.add(paletteColor8ColorPicker);
            palettePickers.add(paletteColor9ColorPicker);
            palettePickers.add(paletteColor10ColorPicker);
            for (int i = 0; i < paletteColorsNumberSpinner.getValue(); i++) {
                g.setColor(fxToSwingColor(palettePickers.get(i).getValue()));
                g.fillRect(
                        paletteXOffsetSpinner.getValue() + artistXPositionSpinner.getValue() + paletteDistanceFromArtistSpinner.getValue() + (paletteHorizontalSpacingSpinner.getValue() * i),
                        paletteYOffsetSpinner.getValue() + albumArtYPositionSpinner.getValue() + albumArtHeightSpinner.getValue() + artistVerticalSpacingSpinner.getValue(),
                        paletteWidthSpinner.getValue(),
                        paletteHeightSpinner.getValue()
                );
            }
        }

        // -- DRAW TRACKS
        int col = 0;
        int row = 0;
        int yOffs = 0;
        for (int j = 0; j < trackList.size(); j++) {
            if (j % numTracksPerColumnSpinner.getValue() == 0 && j != 0) {
                col++;
                row = 0;
                yOffs = 0;
            }
            g.setFont(useGlobalFontFamilyCheckBox.isSelected() ? globalFont : tracksFont);
            g.setColor(useGlobalFontColorCheckBox.isSelected() ? globalFontColor : tracksFontColor1);
            if (!useGlobalFontColorCheckBox.isSelected() && j % 2 == 0 && tracksAlternateColorsCheckBox.isSelected()) {
                g.setColor(useGlobalFontColorCheckBox.isSelected() ? globalFontColor : tracksFontColor2);
            }
            if (highlightExplicitTracksCheckBox.isSelected() && !useGlobalFontColorCheckBox.isSelected() && trackList.get(j).getName().contains("(E)")) {
                g.setColor(fxToSwingColor(explicitTracksHighlightColorPicker.getValue()));
            }
            int threshold = tracksNewlineThresholdSpinner.getValue();

            // TODO: Make this formatting custom
            String track = trackList.get(j).getTrackNumber() + ". " + trackList.get(j).getName();

            if (threshold == 0 || track.length() <= threshold) {
                g.drawString(track,
                        artistXPositionSpinner.getValue() + (col * tracksHorizontalSpacingSpinner.getValue()),
                        ((albumArtYPositionSpinner.getValue() + albumArtHeightSpinner.getValue() + artistVerticalSpacingSpinner.getValue())
                                + 75 + 35 + (row * tracksVerticalSpacingSpinner.getValue()) + yOffs));
                row++;
            } else {
                StringBuilder sb = new StringBuilder(track);
                int i = 0;
                while (i + tracksNewlineThresholdSpinner.getValue() < sb.length()
                        && (i = sb.lastIndexOf(" ", i + 20)) != -1) {
                    sb.replace(i, i + 1, "\n");
                }
                String[] split = sb.toString().split("\n");
                for (i = 1; i < split.length; i++) {
                    split[i] = "   " + split[i];
                }
                System.out.println(Arrays.toString(split));
                for (String s : split) {
                    g.drawString(s,
                            artistXPositionSpinner.getValue() + (col * tracksHorizontalSpacingSpinner.getValue()),
                            ((albumArtYPositionSpinner.getValue() + albumArtHeightSpinner.getValue() + artistVerticalSpacingSpinner.getValue())
                                    + 75 + 35 + (row * tracksVerticalSpacingSpinner.getValue()) + yOffs));
                    yOffs += tracksFontSizeSpinner.getValue();
                }
            }
        }

        // -- DRAW LINES --
        if (enableTopLineCheckBox.isSelected()) {
            g.setColor(fxToSwingColor(topLineColorPicker.getValue()));
            g.setStroke(new BasicStroke((float) topLineThicknessSpinner.getValue()));
            g.drawLine(topLineX1PositionSpinner.getValue(), topLineY1PositionSpinner.getValue(), topLineX2PositionSpinner.getValue(), topLineY2PositionSpinner.getValue());
        }

        if (enableBottomLineCheckBox.isSelected()) {
            g.setColor(fxToSwingColor(bottomLineColorPicker.getValue()));
            g.setStroke(new BasicStroke((float) bottomLineThicknessSpinner.getValue()));
            g.drawLine(bottomLineX1PositionSpinner.getValue(), bottomLineY1PositionSpinner.getValue(), bottomLineX2PositionSpinner.getValue(), bottomLineY2PositionSpinner.getValue());
        }

        // -- DRAW SIGNATURE --
        if (signatureLoaded) {
            try {
                BufferedImage signature = ImageIO.read(signatureFile);
                g.drawImage(
                        signature,
                        signatureXPositionSpinner.getValue(),
                        signatureYPositionSpinner.getValue(),
                        signatureWidthSpinner.getValue(),
                        signatureHeightSpinner.getValue(),
                        signatureFile.getName().substring(signatureFile.getName().length() - 3).equals("png") ?
                                null : fxToSwingColor(postrBackgroundColor.getValue()), null
                );
            } catch (IOException e) {
                addError("Could not load signature file + \"" + signatureFile.getPath() + "\"");
                signatureLoaded = false;
                throw new RuntimeException(e);
            }
        }

        g.dispose();

        if (!Objects.equals(errorMessage, "")) {
            showErrorMessage();
        }

        finalPostrImage = img;

        previewCanvas.getGraphicsContext2D().drawImage(
                SwingFXUtils.toFXImage(img, null), 0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());

        isLoading = false;
    }

    private Font validateFont(ComboBox<String> fontFamilyComboBox, int weight, int size) {
        if (availableFonts.contains(fontFamilyComboBox.getValue())) {
            return new Font(fontFamilyComboBox.getValue(), weight, size);
        } else {
            addError("Font '" + fontFamilyComboBox.getValue() + "' not available on this system");
            return new Font("Arial", weight, size);
        }
    }

    private void showErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                Objects.requireNonNull(AlbumPostrApplication.class.getResource("/stylesheet/ap_style.css")).toExternalForm()
        );
        dialogPane.getStyleClass().add("selectionDialog");
        alert.showAndWait();
        clearErrors();
    }

    private void addError(String s) {
        boolean newLine = Objects.equals(errorMessage, "");
        errorMessage += (s + (newLine ? "\n" : ""));
    }

    private void clearErrors() {
        errorMessage = "";
    }

    private java.awt.Color fxToSwingColor(javafx.scene.paint.Color c) {
        int r = (int) (c.getRed() * 255.0);
        int g = (int) (c.getGreen() * 255.0);
        int b = (int) (c.getBlue() * 255.0);

        return new java.awt.Color(r, g, b);
    }

    int parseWeight(String s) {
        if (s == null) return Font.PLAIN;
        return switch (s) {
            case "Bold" -> Font.BOLD;
            case "Italic" -> Font.ITALIC;
            case "ItalicBold" -> Font.BOLD | Font.ITALIC;
            default -> Font.PLAIN;
        };
    }
}
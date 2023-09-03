module R2TT.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires se.michaelthelin.spotify;
    requires com.google.gson;
    requires org.slf4j;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires java.desktop;

    opens dev.chrismharris.main;
    opens dev.chrismharris.album;
    opens dev.chrismharris.table;

}
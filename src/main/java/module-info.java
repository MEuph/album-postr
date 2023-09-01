module R2TT.main {

    requires javafx.controls;
    requires javafx.fxml;

    requires se.michaelthelin.spotify;
    requires com.google.gson;
    requires org.slf4j;
    requires org.apache.httpcomponents.core5.httpcore5;

    opens dev.chrismharris.main;

}
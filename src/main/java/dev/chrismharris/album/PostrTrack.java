package dev.chrismharris.album;

import dev.chrismharris.main.AlbumPostrApplication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

public class PostrTrack {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty trackNumber;
    private final SimpleIntegerProperty durationMs;

    public PostrTrack(TrackSimplified t) {
        String tName = t.getName();
        tName = tName.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ");
        if (t.getIsExplicit()) {
            tName = tName + " (E)";
        }
        this.name = new SimpleStringProperty(tName);
        this.trackNumber = new SimpleIntegerProperty(t.getTrackNumber());
        this.durationMs = new SimpleIntegerProperty(t.getDurationMs());

        if (AlbumPostrApplication.DEBUG) System.out.println("#" + trackNumber.get() + ": " + name.get() + " [" + durationMs.get() + "ms]");
    }

    public String getName() {
        return name.get();
    }

    public void setName(String s) {
        name.set(s);
    }

    public int getDurationMs() {
        return durationMs.get();
    }

    public void setDurationMs(int i) {
        durationMs.set(i);
    }

    public int getTrackNumber() {
        return trackNumber.get();
    }

    public void setTrackNumber(int i) {
        trackNumber.set(i);
    }
}
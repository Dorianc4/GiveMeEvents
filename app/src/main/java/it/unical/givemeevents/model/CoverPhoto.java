package it.unical.givemeevents.model;

/**
 * Created by Manuel on 9/12/2017.
 */

public class CoverPhoto {
    private String id, source;

    public CoverPhoto(String id, String source) {
        this.id = id;
        this.source = source;
    }

    public CoverPhoto() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

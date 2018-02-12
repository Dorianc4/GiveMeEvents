package it.unical.givemeevents.model;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class CoverPhoto implements Serializable{
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

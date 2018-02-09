package it.unical.givemeevents.model;

/**
 * Created by Manuel on 9/12/2017.
 */

public class Picture {
    private ProfilePictureSource data;

    public Picture() {
    }

    public Picture(ProfilePictureSource data) {
        this.data = data;
    }

    public ProfilePictureSource getData() {
        return data;
    }

    public void setData(ProfilePictureSource data) {
        this.data = data;
    }
}

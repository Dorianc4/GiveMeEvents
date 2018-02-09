package it.unical.givemeevents.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Manuel on 9/12/2017.
 */

public class ProfilePictureSource {
    private int height,width;
    @SerializedName("is_silhouette")
    private boolean isSilhouette;

    public ProfilePictureSource() {
    }

    public ProfilePictureSource(int height, int width, boolean isSilhouette, String url) {
        this.height = height;
        this.width = width;
        this.isSilhouette = isSilhouette;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isSilhouette() {
        return isSilhouette;
    }

    public void setSilhouette(boolean silhouette) {
        isSilhouette = silhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}

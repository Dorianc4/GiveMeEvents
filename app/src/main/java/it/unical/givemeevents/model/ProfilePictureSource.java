package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class ProfilePictureSource implements Parcelable{
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

    public ProfilePictureSource(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        this.isSilhouette = in.readByte()!=0;
        this.url = in.readString();
    }

    public static  final  Creator<ProfilePictureSource> CREATOR = new ClassLoaderCreator<ProfilePictureSource>() {

        @Override
        public ProfilePictureSource createFromParcel(Parcel source, ClassLoader loader) {
            return new ProfilePictureSource(source);
        }

        @Override
        public ProfilePictureSource createFromParcel(Parcel source) {
            return new ProfilePictureSource(source);
        }

        @Override
        public ProfilePictureSource[] newArray(int size) {
            return new ProfilePictureSource[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeByte((byte) (isSilhouette ? 1 : 0));
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

package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class CoverPhoto implements Parcelable{
    private String id, source;

    public CoverPhoto(String id, String source) {
        this.id = id;
        this.source = source;
    }

    public CoverPhoto() {
    }

    public CoverPhoto(Parcel in) {
        this.id = in.readString();
        this.source = in.readString();
    }
    public static  final  Creator<CoverPhoto> CREATOR = new ClassLoaderCreator<CoverPhoto>() {

        @Override
        public CoverPhoto createFromParcel(Parcel source, ClassLoader loader) {
            return new CoverPhoto(source);
        }

        @Override
        public CoverPhoto createFromParcel(Parcel source) {
            return new CoverPhoto(source);
        }

        @Override
        public CoverPhoto[] newArray(int size) {
            return new CoverPhoto[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(source);
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

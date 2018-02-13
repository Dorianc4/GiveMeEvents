package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class Picture implements Parcelable{
    private ProfilePictureSource data;

    public Picture() {
    }

    public Picture(Parcel in) {
        data = in.readParcelable(ProfilePictureSource.class.getClassLoader());
    }
    public static  final  Creator<Picture> CREATOR = new ClassLoaderCreator<Picture>() {

        @Override
        public Picture createFromParcel(Parcel source, ClassLoader loader) {
            return new Picture(source);
        }

        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
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

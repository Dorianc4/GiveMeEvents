package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class Category implements Parcelable{

    private String id;
    private String name;

    public Category() {
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static  final  Creator<Category> CREATOR = new ClassLoaderCreator<Category>() {

        @Override
        public Category createFromParcel(Parcel source, ClassLoader loader) {
            return new Category(source);
        }

        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

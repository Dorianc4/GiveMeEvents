package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yelena on 13/2/2018.
 */

public class Mapeable implements Parcelable{
    private String name;
    private Location location;

    public Mapeable() {
    }

    public Mapeable(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public static final Creator<Mapeable> CREATOR = new ClassLoaderCreator<Mapeable>() {

        @Override
        public Mapeable createFromParcel(Parcel source, ClassLoader loader) {
            return new Mapeable(source);
        }

        @Override
        public Mapeable createFromParcel(Parcel source) {
            return new Mapeable(source);
        }

        @Override
        public Mapeable[] newArray(int size) {
            return new Mapeable[size];
        }

    };


    public Mapeable(Parcel in) {
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(location, flags);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

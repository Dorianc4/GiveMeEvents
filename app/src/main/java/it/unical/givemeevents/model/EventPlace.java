package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class EventPlace implements Parcelable {

    private String id;
    private String name;
    private Location location;
    //ONLY FOR FAVORITES PLACES
    private String picture;

    public EventPlace() {
    }

    public EventPlace(String id, String name, Location location, String picture) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.picture = picture;
    }


    public EventPlace(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.picture = in.readString();
    }

    public static final Creator<EventPlace> CREATOR = new ClassLoaderCreator<EventPlace>() {

        @Override
        public EventPlace createFromParcel(Parcel source, ClassLoader loader) {
            return new EventPlace(source);
        }

        @Override
        public EventPlace createFromParcel(Parcel source) {
            return new EventPlace(source);
        }

        @Override
        public EventPlace[] newArray(int size) {
            return new EventPlace[size];
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
        dest.writeParcelable(location, flags);
        dest.writeString(picture);
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}

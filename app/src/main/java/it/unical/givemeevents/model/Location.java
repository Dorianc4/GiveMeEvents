package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class Location implements Parcelable{

    private String city,country,region,state,street,zip;
    private float latitude,longitude;

    public Location() {
    }

    public Location(String city, String country, String region, String state, String street, String zip, float latitude, float longitude) {
        this.city = city;
        this.country = country;
        this.region = region;
        this.state = state;
        this.street = street;
        this.zip = zip;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public static  final  Creator<Location> CREATOR = new ClassLoaderCreator<Location>() {

        @Override
        public Location createFromParcel(Parcel source, ClassLoader loader) {
            return new Location(source);
        }

        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }

    };
    public Location(Parcel in) {
        this.city = in.readString();
        this.country = in.readString();
        this.region = in.readString();
        this.state = in.readString();
        this.street = in.readString();
        this.zip = in.readString();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(region);
        dest.writeString(state);
        dest.writeString(street);
        dest.writeString(zip);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}

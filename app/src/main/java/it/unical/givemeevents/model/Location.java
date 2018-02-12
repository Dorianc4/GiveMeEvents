package it.unical.givemeevents.model;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class Location implements Serializable{

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

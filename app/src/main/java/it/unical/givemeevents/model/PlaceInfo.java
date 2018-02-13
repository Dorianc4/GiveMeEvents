package it.unical.givemeevents.model;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yelena on 9/2/2018.
 */

public class PlaceInfo {
    private String name, address, phoneNumber, id;
    private Uri webSite;
    private LatLng coordinates;
    private float rating;


    public PlaceInfo(String name, String address, String phoneNumber, String id, Uri webSite, LatLng coordinates,
                     float rating, String attributions) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.webSite = webSite;
        this.coordinates = coordinates;
        this.rating = rating;

    }

    public PlaceInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getWebSite() {
        return webSite;
    }

    public void setWebSite(Uri webSite) {
        this.webSite = webSite;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }



    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id='" + id + '\'' +
                ", webSite=" + webSite +
                ", coordinates=" + coordinates +
                ", rating=" + rating +
                '}';
    }
}

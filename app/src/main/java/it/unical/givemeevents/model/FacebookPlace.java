package it.unical.givemeevents.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class FacebookPlace implements Serializable{

    private String id,name,category;
    private transient Picture picture;
    private String[] emails;
    @SerializedName("category_list")
    private Category[] categoryList;
    private Location location;
    private transient CoverPhoto cover;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture1(Picture picture) {
        this.picture = picture;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public Category[] getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Category[] categoryList) {
        this.categoryList = categoryList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CoverPhoto getCover() {
        return cover;
    }

    public void setCover(CoverPhoto cover) {
        this.cover = cover;
    }
}

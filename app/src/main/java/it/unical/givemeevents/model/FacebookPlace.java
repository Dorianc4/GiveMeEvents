package it.unical.givemeevents.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class FacebookPlace implements Parcelable, Serializable{

    private String id,name,category;
    private  Picture picture;
    private String[] emails;
    @SerializedName("category_list")
    private Category[] categoryList;
    private Location location;
    private CoverPhoto cover;

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

    public FacebookPlace(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.category = in.readString();
        this.picture = in.readParcelable(Picture.class.getClassLoader());
//        this.emails = in.createStringArray();
//        int size = in.readInt();
//        if(size==0){
//            this.categoryList = new Category[0];
//        }else {
//            this.categoryList = new Category[size];
//            in.readTypedArray(this.categoryList, Category.CREATOR);
            this.categoryList = (Category[]) in.readSerializable();
//        }
        this.location = in.readParcelable(Location.class.getClassLoader());
//        this.cover = in.readParcelable(CoverPhoto.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeParcelable(picture,flags);
//        dest.writeArray(emails);
//        if(categoryList==null || categoryList.length==0){
//            dest.writeInt(0);
//        }else{
//            dest.writeInt(categoryList.length);
            dest.writeSerializable(categoryList);
//        }
        dest.writeParcelable(location, flags);
//        dest.writeParcelable(cover, flags);
    }

    public static  final  Creator<FacebookPlace> CREATOR = new ClassLoaderCreator<FacebookPlace>() {

        @Override
        public FacebookPlace createFromParcel(Parcel source, ClassLoader loader) {
            return new FacebookPlace(source);
        }

        @Override
        public FacebookPlace createFromParcel(Parcel source) {
            return new FacebookPlace(source);
        }

        @Override
        public FacebookPlace[] newArray(int size) {
            return new FacebookPlace[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

}

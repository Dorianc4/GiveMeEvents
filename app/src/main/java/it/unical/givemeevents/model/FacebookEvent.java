package it.unical.givemeevents.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Manuel on 9/12/2017.
 */

public class FacebookEvent implements Serializable{

    private String id;
    private String type;
    private  Picture picture;
    @SerializedName("attending_count")
    private int attendingCount;
    @SerializedName("noreply_count")
    private String noReplyCount;
    private String category;
    private  CoverPhoto cover;
    @SerializedName("declined_count")
    private int declinedCount;
    private String description;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("interested_count")
    private int interestedCount;
    private int maybeCount;
    private String name;
    private EventPlace place;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("ticket_uri")
    private String tickedUri;
    @SerializedName("ticketing_privacy_uri")
    private String ticketingPrivacyUri;
    @SerializedName("ticketing_terms_uri")
    private String ticketingTermsUri;
    private FacebookPlace placeOwner;

    public FacebookEvent() {
    }

    public FacebookEvent(String id, String type, Picture picture, int attendingCount, String noReplyCount, String category, CoverPhoto cover, int declinedCount, String description, String endTime, int interestedCount, int maybeCount, String name, EventPlace place, String startTime, String tickedUri, String ticketingPrivacyUri, String ticketingTermsUri) {
        this.id = id;
        this.type = type;
        this.picture = picture;
        this.attendingCount = attendingCount;
        this.noReplyCount = noReplyCount;
        this.category = category;
        this.cover = cover;
        this.declinedCount = declinedCount;
        this.description = description;
        this.endTime = endTime;
        this.interestedCount = interestedCount;
        this.maybeCount = maybeCount;
        this.name = name;
        this.place = place;
        this.startTime = startTime;
        this.tickedUri = tickedUri;
        this.ticketingPrivacyUri = ticketingPrivacyUri;
        this.ticketingTermsUri = ticketingTermsUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int getAttendingCount() {
        return attendingCount;
    }

    public void setAttendingCount(int attendingCount) {
        this.attendingCount = attendingCount;
    }

    public String getNoReplyCount() {
        return noReplyCount;
    }

    public void setNoReplyCount(String noReplyCount) {
        this.noReplyCount = noReplyCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CoverPhoto getCover() {
        return cover;
    }

    public void setCover(CoverPhoto cover) {
        this.cover = cover;
    }

    public int getDeclinedCount() {
        return declinedCount;
    }

    public void setDeclinedCount(int declinedCount) {
        this.declinedCount = declinedCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(int interestedCount) {
        this.interestedCount = interestedCount;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public void setMaybeCount(int maybeCount) {
        this.maybeCount = maybeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventPlace getPlace() {
        return place;
    }

    public void setPlace(EventPlace place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTickedUri() {
        return tickedUri;
    }

    public void setTickedUri(String tickedUri) {
        this.tickedUri = tickedUri;
    }

    public String getTicketingPrivacyUri() {
        return ticketingPrivacyUri;
    }

    public void setTicketingPrivacyUri(String ticketingPrivacyUri) {
        this.ticketingPrivacyUri = ticketingPrivacyUri;
    }

    public String getTicketingTermsUri() {
        return ticketingTermsUri;
    }

    public void setTicketingTermsUri(String ticketingTermsUri) {
        this.ticketingTermsUri = ticketingTermsUri;
    }

    public FacebookPlace getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(FacebookPlace placeOwner) {
        this.placeOwner = placeOwner;
    }
}

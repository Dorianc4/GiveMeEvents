package it.unical.givemeevents.model;

import java.util.Date;

/**
 * Created by Manuel on 6/12/2017.
 */

public class GraphSearchData {

    private double latitud,longitud;
    private String query;
    private int distance,limit;
    private Date since,until;
    private boolean showActiveOnly;
    private String authToken;
    private String[] categories;

    public GraphSearchData(float latitud, float longitud, String query, int distance, int limit, Date since, Date until, boolean showActiveOnly, String[] categories , String authToken) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.query = query;
        this.distance = distance;
        this.limit = limit;
        this.since = since;
        this.until = until;
        this.showActiveOnly = showActiveOnly;
        this.authToken = authToken;
        this.categories = categories;

    }

    public GraphSearchData() {
        this.distance = 100;
//        this.categories = new String[]{"ARTS_ENTERTAINMENT", "EDUCATION", "FITNESS_RECREATION", "FOOD_BEVERAGE", "HOTEL_LODGING", "MEDICAL_HEALTH", "SHOPPING_RETAIL", "TRAVEL_TRANSPORTATION"};
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Date getSince() {
        if(since==null)
            return new Date();
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public boolean isShowActiveOnly() {
        return showActiveOnly;
    }

    public void setShowActiveOnly(boolean showActiveOnly) {
        this.showActiveOnly = showActiveOnly;
    }

    public String getCenter(){
        return getLatitud()+","+getLongitud();
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}

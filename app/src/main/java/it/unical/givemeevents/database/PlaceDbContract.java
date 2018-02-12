package it.unical.givemeevents.database;

import android.provider.BaseColumns;

/**
 * Created by Manuel on 11/2/2018.
 */

public final class PlaceDbContract {
    private PlaceDbContract() {
    }

    /* Inner class that defines the table contents */
    public static class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "fav_place";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_STREET = "street";
        public static final String COLUMN_NAME_PICTURE = "picture";

    }
}
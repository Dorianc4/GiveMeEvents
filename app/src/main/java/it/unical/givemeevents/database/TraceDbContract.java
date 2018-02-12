package it.unical.givemeevents.database;

import android.provider.BaseColumns;

/**
 * Created by Manuel on 11/2/2018.
 */

public final class TraceDbContract {
    private TraceDbContract() {
    }

    /* Inner class that defines the table contents */
    public static class TraceEntry implements BaseColumns {
        public static final String TABLE_NAME = "event_trace";
        public static final String COLUMN_NAME_ID_PLACE = "id_place";
        public static final String COLUMN_NAME_START_TIME = "start_time";
    }
}

package it.unical.givemeevents.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import it.unical.givemeevents.R;

/**
 * Created by Manuel on 11/2/2018.
 */

public class GiveMeEventsDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    static final String CATEGORY_TRACE_TABLE_NAME = "category_trace";
    static final String COLUMN_NAME_ID_CATEGORY = "id_category";

    private static final String SQL_CREATE_TABLE_FAVPLACE = "CREATE TABLE IF NOT EXISTS " +
            PlaceDbContract.PlaceEntry.TABLE_NAME + " (" +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE + DOUBLE_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE + DOUBLE_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE + TEXT_TYPE + " ); ";

    private static final String SQL_CREATE_TABLE_EVENT_TRACE = "CREATE TABLE IF NOT EXISTS " +
            TraceDbContract.TraceEntry.TABLE_NAME + " (" +
            TraceDbContract.TraceEntry.COLUMN_NAME_ID_PLACE + TEXT_TYPE + " NOT NULL ON CONFLICT ROLLBACK," +
            TraceDbContract.TraceEntry.COLUMN_NAME_START_TIME + TEXT_TYPE + " ); ";

    private static final String SQL_CREATE_TABLE_CATEGORY_TRACE = "CREATE TABLE IF NOT EXISTS " +
            CATEGORY_TRACE_TABLE_NAME + " (" +
            COLUMN_NAME_ID_CATEGORY + TEXT_TYPE + " PRIMARY KEY NOT NULL);";


    public GiveMeEventsDbHelper(Context context) {
        super(context, context.getString(R.string.db_name), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVPLACE + SQL_CREATE_TABLE_EVENT_TRACE + SQL_CREATE_TABLE_CATEGORY_TRACE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

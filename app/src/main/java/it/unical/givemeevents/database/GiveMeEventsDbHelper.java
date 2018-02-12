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

    private static final String SQL_CREATE_TABLES = "CREATE TABLE IF NOT EXISTS " +
            PlaceDbContract.PlaceEntry.TABLE_NAME + " (" +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE + DOUBLE_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE + DOUBLE_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP +
            PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE + TEXT_TYPE + " )";


    public GiveMeEventsDbHelper(Context context) {
        super(context, context.getString(R.string.db_name), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

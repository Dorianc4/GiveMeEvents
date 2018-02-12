package it.unical.givemeevents.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import it.unical.givemeevents.model.EventPlace;

/**
 * Created by Manuel on 11/2/2018.
 */

public class GiveMeEventDbManager {

    private SQLiteOpenHelper openhelper;

    public GiveMeEventDbManager(Context context) {
        openhelper = new GiveMeEventsDbHelper(context);
    }

    public Cursor getAllFavPlaces() {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from " + PlaceDbContract.PlaceEntry.TABLE_NAME, null);
    }

    public ContentValues getFavPlace(long id) {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        ContentValues row = new ContentValues();
        Cursor cur = db.rawQuery("select * from " + PlaceDbContract.PlaceEntry.TABLE_NAME + " where " + PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        if (cur.moveToNext()) {
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_ID, cur.getLong(0));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME, cur.getString(1));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY, cur.getString(2));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY, cur.getString(3));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE, cur.getDouble(4));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE, cur.getDouble(5));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET, cur.getString(6));
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE, cur.getString(7));

        }
        cur.close();
        db.close();
        return row;
    }

    public long addFavPlace(EventPlace place) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null && place == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_ID, place.getId());
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME, place.getName());
        if (place.getLocation() != null) {
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY, place.getLocation().getCity());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY, place.getLocation().getCountry());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE, place.getLocation().getLatitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE, place.getLocation().getLongitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET, place.getLocation().getStreet());
        }
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE, place.getPicture());
        long id = db.insert(PlaceDbContract.PlaceEntry.TABLE_NAME, null, row);
        db.close();
        return id;
    }

    public long addorReplaceFavPlace(EventPlace place) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null || place == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_ID, place.getId());
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME, place.getName());
        if (place.getLocation() != null) {
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY, place.getLocation().getCity());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY, place.getLocation().getCountry());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE, place.getLocation().getLatitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE, place.getLocation().getLongitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET, place.getLocation().getStreet());
        }
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE, place.getPicture());
        long id = db.insertWithOnConflict(PlaceDbContract.PlaceEntry.TABLE_NAME, null, row, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return id;
    }

    public void deleteFavEvent(long id) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(PlaceDbContract.PlaceEntry.TABLE_NAME, PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(EventPlace place) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null || place == null) {
            return;
        }
        ContentValues row = new ContentValues();
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_ID, place.getId());
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_NAME, place.getName());
        if (place.getLocation() != null) {
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_CITY, place.getLocation().getCity());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_COUNTRY, place.getLocation().getCountry());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LATITUDE, place.getLocation().getLatitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_LONGITUDE, place.getLocation().getLongitude());
            row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_STREET, place.getLocation().getStreet());
        }
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE, place.getPicture());
        db.update(PlaceDbContract.PlaceEntry.TABLE_NAME, row, PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(place.getId())});
        db.close();
    }
}

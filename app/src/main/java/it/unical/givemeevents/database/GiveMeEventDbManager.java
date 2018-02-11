package it.unical.givemeevents.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Manuel on 11/2/2018.
 */

public class GiveMeEventDbManager {

    private SQLiteOpenHelper openhelper;

    /**
     * Construct a new database helper object
     *
     * @param context The current context for the application or activity
     */
    public GiveMeEventDbManager(Context context) {
        openhelper = new GiveMeEventsDbHelper(context);
    }

    /**
     * Return a cursor object with all rows in the table.
     *
     * @return A cursor suitable for use in a SimpleCursorAdapter
     */
    public Cursor getAllFavPlaces() {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from " + PlaceDbContract.PlaceEntry.TABLE_NAME + " order by priority, title", null);
    }

    /**
     * Return values for a single row with the specified id
     *
     * @param id The unique id for the row o fetch
     * @return All column values are stored as properties in the ContentValues object
     */
    public ContentValues getFavPlace(long id) {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        ContentValues row = new ContentValues();
        Cursor cur = db.rawQuery("select title, priority from " + PlaceDbContract.PlaceEntry.TABLE_NAME + " where " + PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        if (cur.moveToNext()) {
            row.put("title", cur.getString(0));
            row.put("priority", cur.getInt(1));
        }
        cur.close();
        db.close();
        return row;
    }

    /**
     * Add a new row to the database table
     *
     * @param title    The title value for the new row
     * @param priority The priority value for the new row
     * @return The unique id of the newly added row
     */
    public long add(String title, int priority) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put("title", title);
        row.put("priority", priority);
        long id = db.insert("todos", null, row);
        db.close();
        return id;
    }

    /**
     * Delete the specified row from the database table. For simplicity reasons, nothing happens if
     * this operation fails.
     *
     * @param id The unique id for the row to delete
     */
    public void delete(long id) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete("todos", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Updates a row in the database table with new column values, without changing the unique id of the row.
     * For simplicity reasons, nothing happens if this operation fails.
     *
     * @param id       The unique id of the row to update
     * @param title    The new title value
     * @param priority The new priority value
     */
    public void update(long id, String title, int priority) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues row = new ContentValues();
        row.put("title", title);
        row.put("priority", priority);
        db.update("todos", row, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

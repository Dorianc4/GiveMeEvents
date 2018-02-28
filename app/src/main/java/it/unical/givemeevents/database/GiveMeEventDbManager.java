package it.unical.givemeevents.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import it.unical.givemeevents.model.Category;
import it.unical.givemeevents.model.EventPlace;
import it.unical.givemeevents.model.FacebookPlace;
import it.unical.givemeevents.model.Location;

/**
 * Created by Manuel on 11/2/2018.
 */

public class GiveMeEventDbManager {

    private SQLiteOpenHelper openhelper;

    public GiveMeEventDbManager(Context context) {
        openhelper = new GiveMeEventsDbHelper(context);
    }

//lista de lugares favoritos
    public List<EventPlace> getAllFavPlaces() {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor c = db.rawQuery("select * from " + PlaceDbContract.PlaceEntry.TABLE_NAME, null);
        List<EventPlace> eventList = new ArrayList<>();
        if (c != null) {
            while (c.moveToNext()) {
                Location loc = new Location();
                loc.setLatitude(c.getFloat(4));
                loc.setLongitude(c.getFloat(5));
                loc.setCity(c.getString(2));
                loc.setCountry(c.getString(3));
                loc.setStreet(c.getString(6));
                EventPlace evt = new EventPlace(c.getLong(0) + "", c.getString(1), loc, c.getString(7));
                eventList.add(evt);
            }
        }
        c.close();
        db.close();
        return eventList;
    }
//dame un lugar favorito
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
//adiciona un lugar de evento favorito
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
//add un lugar de facebook favorito
    public long addFavPlaceOwner(FacebookPlace place) {
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
        row.put(PlaceDbContract.PlaceEntry.COLUMN_NAME_PICTURE, place.getPicture().getData().getUrl());
        long id = db.insert(PlaceDbContract.PlaceEntry.TABLE_NAME, null, row);
        db.close();
        return id;
    }
//adiciona o remplaza un lugar favorito
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
//eliminar un evento favorito
    public void deleteFavEvent(long id) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        db.delete(PlaceDbContract.PlaceEntry.TABLE_NAME, PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
//actualizr un lugar
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
//verifica si un lugar ya esta como favorito
    public boolean existFavPlace(String id) {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null || id == "0") {
            return false;
        }
        boolean flag = false;
        Cursor cursor = db.rawQuery("select count(*) from " + PlaceDbContract.PlaceEntry.TABLE_NAME + " where " + PlaceDbContract.PlaceEntry.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                flag = true;
            }
        }
        cursor.close();
        db.close();
        return flag;
    }
//si esta la actualiza si no la adiciona tazas de un lugar y la hora
    public long addorReplaceTrace(String id_place, String time) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null || id_place == null || time == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put(TraceDbContract.TraceEntry.COLUMN_NAME_ID_PLACE, id_place);
        row.put(TraceDbContract.TraceEntry.COLUMN_NAME_START_TIME, time);
        long id = db.insertWithOnConflict(TraceDbContract.TraceEntry.TABLE_NAME, null, row, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return id;
    }
//dame todas las trazas
    public Cursor getAllTraces() {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from " + TraceDbContract.TraceEntry.TABLE_NAME, null);
    }
//dame todas las categorias
    public Cursor getAllTraceCategories() {
        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from " + GiveMeEventsDbHelper.CATEGORY_TRACE_TABLE_NAME, null);
    }
//adiciona o remplaz
    public long addorReplaceTraceCategory(String id_category) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null || id_category == null) {
            return 0;
        }
        ContentValues row = new ContentValues();
        row.put(GiveMeEventsDbHelper.COLUMN_NAME_ID_CATEGORY, id_category);
        long id = db.insertWithOnConflict(GiveMeEventsDbHelper.CATEGORY_TRACE_TABLE_NAME, null, row, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return id;
    }
//si esta lo actualiza si no esta lo inserta
    public void addorReplaceCategoryTrace(Category[] categories) {
        SQLiteDatabase db = openhelper.getWritableDatabase();
        if (db == null || categories == null) {
            return;
        }
        for (Category cat : categories) {
            addorReplaceTraceCategory(cat.getId());
        }
        db.close();
    }
//lista de las categorias mas visitadas
    public List<String> getCategoriesMostVisited() {

        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id_category FROM " + GiveMeEventsDbHelper.CATEGORY_TRACE_TABLE_NAME + " GROUP BY id_category ORDER BY count(id_category) DESC LIMIT 5", null);
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return ids;
    }
//lista de lugares lugares mas visitado
    public List<String> getPlacesMostVisited() {

        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id_place FROM " + TraceDbContract.TraceEntry.TABLE_NAME + " GROUP BY id_place ORDER BY count(id_place) DESC LIMIT 5", null);
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return ids;
    }
//horario preferido por el usuario para visitar eventos
    public int getTimeMostCommon() {

        SQLiteDatabase db = openhelper.getReadableDatabase();
        if (db == null) {
            return 0;
        }
        int fnal = 0;
        //////DAY COUNT///////
        Cursor cursor = db.rawQuery("SELECT count(start_time) as day FROM " + TraceDbContract.TraceEntry.TABLE_NAME + " WHERE start_time >= ? AND start_time <= ?", new String[]{"07:00", "12:59"});
        int day = 0;
        if (cursor.moveToNext()) {
            day = cursor.getInt(0);
        }
        cursor.close();

        //////AFTERNOON///////
        Cursor cursor1 = db.rawQuery("SELECT count(start_time) as afternoon FROM " + TraceDbContract.TraceEntry.TABLE_NAME + " WHERE start_time >= ? AND start_time <= ?", new String[]{"13:00", "20:59"});
        int after = 0;
        if (cursor1.moveToNext()) {
            after = cursor1.getInt(0);
        }
        cursor1.close();

        //////AFTERNOON///////
        Cursor cursor2 = db.rawQuery("SELECT count(start_time) as night FROM " + TraceDbContract.TraceEntry.TABLE_NAME + " WHERE start_time >= ? AND start_time <= ?", new String[]{"21:00", "06:59"});
        int night = 0;
        if (cursor2.moveToNext()) {
            night = cursor2.getInt(0);
        }
        cursor2.close();
        if (night >= after && night >= day) {
            fnal = 3;
        } else if (after >= night && after > +day) {
            fnal = 2;
        } else if (day >= night && day >= after) {
            fnal = 1;
        }
        return fnal;
    }
}

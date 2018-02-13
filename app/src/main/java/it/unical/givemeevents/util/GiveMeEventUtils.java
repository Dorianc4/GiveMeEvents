package it.unical.givemeevents.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.unical.givemeevents.R;

/**
 * Created by Manuel on 6/2/2018.
 */

public class GiveMeEventUtils {

    public final static int ACCESS_FINE_LOCATION_CODE = 25;

    public static AlertDialog showYesNoDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener yes, DialogInterface.OnClickListener no) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        if (title != null && !title.equals("")) {
            builder.setTitle(title);
        } else {
            builder.setTitle(R.string.app_name);
        }
        builder.setMessage(msg);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton(ctx.getString(R.string.btn_yes), yes);
        builder.setNegativeButton(ctx.getString(R.string.btn_no), no);
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static void showToast(Context ctx, String msg) {

        if (ctx != null && !msg.equals("")) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showToast(Context ctx, int msg) {

        if (msg > 0) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static SharedPreferences getPreferences(Context ctx) {

        return ctx.getSharedPreferences(ctx.getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);

    }

    public static void setPreference(Context ctx, String name, String value) {
        if (ctx != null && name != null && value != null) {
            if (!name.equals("")) {
                SharedPreferences.Editor editor = GiveMeEventUtils.getPreferences(ctx).edit();
                editor.putString(name, value);
                editor.commit();
            }
        }
    }

    public static void setPreference(Context ctx, String name, long value) {
        if (ctx != null && name != null) {
            if (!name.equals("") && value != 0) {
                SharedPreferences.Editor editor = GiveMeEventUtils.getPreferences(ctx).edit();
                editor.putLong(name, value);
                editor.commit();
            }
        }
    }

    public static void setPreference(Context ctx, String name, boolean value) {
        if (ctx != null && name != null) {
            SharedPreferences.Editor editor = GiveMeEventUtils.getPreferences(ctx).edit();
            editor.putBoolean(name, value);
            editor.commit();
        }
    }

    public static DatePickerDialog showDatePicker(Context ctx, DatePickerDialog.OnDateSetListener listener) {
        if (ctx != null && listener != null) {
            Calendar date = new GregorianCalendar();
            DatePickerDialog picker = new DatePickerDialog(ctx, listener, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH));
            picker.show();
            return picker;
        }
        return null;
    }

    public static Date createDateFromString(String date) {
        if (date != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sd.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Date createDateFromString(String date, String pattern) {
        if (date != null && pattern != null && !pattern.isEmpty()) {
            SimpleDateFormat sd = new SimpleDateFormat(pattern);
            try {
                return sd.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String createStringfromDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sd = new SimpleDateFormat(pattern);
            return sd.format(date);
        }
        return "";
    }

    public static void launchBrowser(Context ctx, String url) {
        if (ctx != null && !url.isEmpty()) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent();
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }


}

package it.unical.givemeevents.services;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import it.unical.givemeevents.R;
import it.unical.givemeevents.util.GiveMeEventUtils;

/**
 * Created by Yelena on 17/12/2017.
 */

public class CustomLocationManager extends Service {
    private Location mylocation;
    private LocationManager locManager;
    private Context mActivity;
    private double longitude;
    private double latitude;

    public CustomLocationManager(Context act) {
        mActivity = act;
        mylocation = null;
        locManager = (LocationManager) mActivity.getSystemService(mActivity.LOCATION_SERVICE);
        longitude = 0.00;
        latitude = 0.00;
    }

    public void getLocation(LocationListener listener) {
        // CheckPerm();
        try {
            if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (mylocation == null) {
                    locManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            2 * 20 * 1000,
                            500, listener);
//                    locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
                    if (locManager != null) {
                        mylocation = locManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mylocation != null) {
                            latitude = mylocation.getLatitude();
                            longitude = mylocation.getLongitude();
                        }
                    }
                }

            } else {
                locManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        2 * 20 * 1000,
                        500, listener);
                mylocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } catch (SecurityException e) {
            GiveMeEventUtils.showToast(mActivity, R.string.grant_location_permission);
//            Toast.makeText(mActivity, mActivity.getString(R.string.grant_location_permission), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            throw e;
        }
    }

    public void removeUpdates(LocationListener listener) {
        locManager.removeUpdates(listener);
    }

    public boolean isLocationEnabled() {
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public Location getLastKnownLocation() {
        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            return locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }


    public double Longitude() {
        return longitude;
    }

    public double Latitude() {
        return latitude;
    }


//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
//
//        // Setting Dialog Title
//        alertDialog.setTitle("Location Settings");
//
//        // Setting Dialog Message
//        alertDialog.setMessage("Location is not enabled. Do you want to go to settings menu?");
//
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                mActivity.startActivity(intent);
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }

//    @Override
//    public void onLocationChanged(final Location location) {
//
//        //mylocation = location;
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

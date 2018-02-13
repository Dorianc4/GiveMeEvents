package it.unical.givemeevents;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unical.givemeevents.model.EventPlace;
import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.model.FacebookPlace;
import it.unical.givemeevents.model.Place;

/**
 * Created by Yelena on 10/2/2018.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71, 136));

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    //vars
    private Boolean mLocationPermissionGRanted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient mGeoDataClient;
    private ArrayList<Parcelable> places;
    private FacebookEvent event;
    private String Name;
    private it.unical.givemeevents.model.Location location;
    private boolean single_event;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGRanted) {
            getDeviceLocation();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        init();
        if(single_event){
            ShowonMap();
        }else{
            ShowAllMap();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        //mGps = (ImageView) findViewById(R.id.ic_gps);
        getLocationPermission();
        if(getCallingActivity()!=null){
            if(getCallingActivity().getClassName().equals(EventDetails.class.getName())){
               Name = getIntent().getStringExtra("Name");
               location = getIntent().getParcelableExtra("Location");
                single_event = true;
            }else if(getCallingActivity().getClassName().equals(MainActivity.class.getName())){
                places =  getIntent().getParcelableArrayListExtra("eventList");
                single_event = false;
            }
        }
    }

    private void init(){
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mGeoDataClient = Places.getGeoDataClient(this, null);
    }

    private void geoLocate(){
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<Address>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);

        }catch (IOException e){

        }
        if(list.size()>0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found and address at: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGRanted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation =(Location) task.getResult();
                            // moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "Current Location");
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }catch (SecurityException e){

        }
    }
    private void moveCamera(LatLng latlng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        if(title != "Current Location"){
            MarkerOptions options = new MarkerOptions()
                    .position(latlng)
                    .title(title);
            mMap.addMarker(options);
        }
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGRanted=true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }

        }else{
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGRanted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int i =0; i < grantResults.length; i++){
                        if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGRanted =false;
                            return;
                        }
                    }
                    mLocationPermissionGRanted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void ShowonMap(){
        float latitude = location.getLatitude();
        float longitude = location.getLongitude();


        LatLng coordinates = new LatLng(latitude, longitude);
        /*mMap.addMarker(new MarkerOptions().position(coordinates)
                .title(Name));*/

        moveCamera(coordinates, DEFAULT_ZOOM, Name);
    }


    private void ShowAllMap(){
        float latitude = 0;
        float longitude = 0;
        String evName = "";
        for(int i = 0; i < places.size(); i++){
            if(places.get(i) instanceof EventPlace){
                EventPlace pl1 = (EventPlace) places.get(i);
                latitude = pl1.getLocation().getLatitude();
                longitude = pl1.getLocation().getLongitude();
                LatLng coordinates = new LatLng(latitude, longitude);
                 evName = pl1.getName();

            }else if(places.get(i) instanceof FacebookPlace){
                FacebookPlace pl2 = (FacebookPlace) places.get(i);
                latitude = pl2.getLocation().getLatitude();
                longitude = pl2.getLocation().getLongitude();

                 evName = pl2.getName();
            }
            LatLng coordinates = new LatLng(latitude, longitude);
            moveCamera(coordinates, DEFAULT_ZOOM, evName);
        }
    }
}

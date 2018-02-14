package it.unical.givemeevents;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.unical.givemeevents.database.GiveMeEventDbManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.util.GiveMeEventUtils;
import it.unical.givemeevents.model.Location;
import it.unical.givemeevents.util.GiveMeEventUtils;

public class EventDetails extends AppCompatActivity {

    private FacebookEvent event;
    private TextView txt_Name;
    private TextView txt_Place;
    private TextView txt_Adress;
    private TextView txt_Date;
    private TextView txt_Time;
    private TextView txt_Description;
    private TextView txt_Category;
    private ImageView img_Event;
    private ImageButton btn_Map;
    private ImageButton btn_Calendar;
    private ImageButton btn_Favorite;
    private String Place;
    private Location location;
    private GiveMeEventDbManager dbM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txt_Adress =(TextView) findViewById(R.id.txt_EvAddress);
        txt_Date = ((TextView) findViewById(R.id.txt_EvDate));
        txt_Description = (TextView) findViewById(R.id.txt_EvDesc);
        txt_Name = (TextView) findViewById(R.id.txt_EvName);
        txt_Time = (TextView) findViewById(R.id.txt_EvTime);
        txt_Place = (TextView) findViewById(R.id.txt_EvPlace);
        txt_Category = (TextView) findViewById(R.id.txt_EvCategory);
        img_Event = (ImageView) findViewById(R.id.img_EvImage);
        btn_Favorite = (ImageButton) findViewById(R.id.btn_plFavorite);
        btn_Map = (ImageButton) findViewById(R.id.btn_plMap);
        btn_Calendar = (ImageButton) findViewById(R.id.btn_AddCalendar);

        event = getIntent().getParcelableExtra("Event");
        int i = 0;

        String Name = event.getName();
        String Description = event.getDescription();

        Date evdate = GiveMeEventUtils.createDateFromString(event.getStartTime(), "yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat myFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String Date = GiveMeEventUtils.createStringfromDate(evdate, "EEE, d MMM yyyy");
        String Time = GiveMeEventUtils.createStringfromDate(evdate, "HH:mm");


        String Address= "";

        if (event.getPlace() != null) {
             Place = event.getPlace().getName();
             Address = event.getPlace().getLocation().getStreet() + ", " + event.getPlace().getLocation().getCity() + ", " +
                    event.getPlace().getLocation().getZip() + ", " + event.getPlace().getLocation().getCountry();
             location = event.getPlace().getLocation();


        } else if (event.getPlaceOwner() != null) {
             Place = event.getPlaceOwner().getName();

            Address = event.getPlaceOwner().getLocation().getStreet() + ", " + event.getPlaceOwner().getLocation().getCity() + ", " +
                    event.getPlaceOwner().getLocation().getZip() + ", " + event.getPlaceOwner().getLocation().getCountry();
            location = event.getPlaceOwner().getLocation();
        }


        String Category = event.getCategory();


        txt_Category.setText(Category);
        txt_Place.setText(Place);
        txt_Time.setText(Time);
        txt_Name.setText(Name);
        txt_Description.setText(Description);
        txt_Date.setText(Date);
        txt_Adress.setText(Address);

        if (event.getPicture() != null) {
            Picasso.with(this).load(event.getPicture().getData().getUrl()).placeholder(R.drawable.imagen).into(img_Event);
        }

        btn_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(EventDetails.this, MapActivity.class);
                mapIntent.putExtra("Name", Place);
                mapIntent.putExtra("Location", location);
                ActivityCompat.startActivityForResult(EventDetails.this, mapIntent, 0, null);
            }
        });
        dbM = new GiveMeEventDbManager(EventDetails.this);
        if(event.getPlace()!=null){
            if((dbM.existFavPlace(event.getPlace().getId()) )){
                btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_on));
            }else{
                btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_off));
            }
        }else{
            if( (dbM.existFavPlace(event.getPlaceOwner().getId()))){
                btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_on));
            }else{
                btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_off));
            }
        }
        btn_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    boolean favorite;
                    if(event.getPlace()!=null){
                        favorite = dbM.existFavPlace(event.getPlace().getId());
                    }else{
                        favorite = dbM.existFavPlace(event.getPlaceOwner().getId());
                    }

                    if (!favorite) {
                        if(event.getPlace()!=null) {
                            dbM.addFavPlace(event.getPlace());
                        }else{
                            dbM.addFavPlaceOwner(event.getPlaceOwner());
                        }
                        btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_on));

                    } else {
                        if(event.getPlace()!=null) {
                            dbM.deleteFavEvent(new Long(event.getPlaceOwner().getId()));
                        }else{
                            dbM.deleteFavEvent(new Long(event.getPlaceOwner().getId()));
                        }

                        btn_Favorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav_off));
                    }
                }
        });

        btn_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckandWriteCalendar();
            }
        });
    }

    public void CheckandWriteCalendar(){
        if (ContextCompat.checkSelfPermission(EventDetails.this,
                Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventDetails.this,  new String[] {  Manifest.permission.WRITE_CALENDAR},
                    GiveMeEventUtils.WRITE_CALENDAR_CODE);

        }else{
            GiveMeEventUtils.addEventToCalendar(EventDetails.this, event);
            Date evdate = GiveMeEventUtils.createDateFromString(event.getStartTime(), "yyyy-MM-dd'T'HH:mm:ssZ");
            SimpleDateFormat myFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

            String Time = GiveMeEventUtils.createStringfromDate(evdate, "HH:mm");
            if(event.getPlace()!=null) {
                String id = event.getPlace().getId();
                dbM.addorReplaceTrace(id, Time);
            }else{
                String id = event.getPlaceOwner().getId();
                dbM.addorReplaceTrace(id, Time);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GiveMeEventUtils.WRITE_CALENDAR_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CheckandWriteCalendar();
                } else {
                    GiveMeEventUtils.showToast(EventDetails.this, getString(R.string.permission_denied));
                }
                break;
        }
    }

}

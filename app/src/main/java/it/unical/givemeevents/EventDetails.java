package it.unical.givemeevents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.unical.givemeevents.model.FacebookEvent;
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
    private ImageButton btn_Favorite;
    private String Place;
    private Location location;

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

        event = getIntent().getParcelableExtra("Event");

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

    }

}

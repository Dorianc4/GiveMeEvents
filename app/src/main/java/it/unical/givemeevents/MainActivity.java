package it.unical.givemeevents;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import it.unical.givemeevents.adapter.EventAdapter;
import it.unical.givemeevents.adapter.RecycleViewAdapter;
import it.unical.givemeevents.database.GiveMeEventDbManager;
import it.unical.givemeevents.gui.FilterSearchDialog;
import it.unical.givemeevents.model.EventPlace;
import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.model.GraphSearchData;
import it.unical.givemeevents.network.FacebookGraphManager;
import it.unical.givemeevents.services.CustomLocationManager;
import it.unical.givemeevents.util.GiveMeEventUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private TextView textView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ListView eventsList;
    //    private EventAdapter adapter;
    private NavigationView navigationView;
    private CustomLocationManager locManager;
    private LocationListener locListener;
    private AsyncTask<Void, List<FacebookEvent>, List<FacebookEvent>> asyncFindEvents;
    private boolean isLargeLayout;
    private GraphSearchData gsd;
    private RecyclerView myRecycle;
    private RecycleViewAdapter myAdapter;
    private GiveMeEventDbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLargeLayout = getResources().getBoolean(R.bool.large_layout);
        ///////////////////////MENU AND TOOLBAR//////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////////////////////////////////////////////////////////

        ////////////////////FACEBOOK LOGIN///////////////////////
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager, new CustomLoginFacebookCallback(this));
//        LoginManager.getInstance().logInWithReadPermissions(this, null);
        ///////////////////////////////////////////////////////////

        //////////////////CREATING LOCATION MANAGER////////////////
        locManager = new CustomLocationManager(this);
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gsd.setLatitud(location.getLatitude());
                gsd.setLongitud(location.getLongitude());
                perform();
                locManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        ////////////////////////RECYCLE VIEW///////////////////////////////////
        myRecycle = (RecyclerView) findViewById(R.id.cardView);
        myRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        myAdapter = new RecycleViewAdapter(new ArrayList<FacebookEvent>(), this);
        myRecycle.setAdapter(myAdapter);

        gsd = new GraphSearchData(500, getResources().getStringArray(R.array.fb_graph_field_categories));
        /*eventsList = (ListView) findViewById(R.id.eventsList);
        adapter = new EventAdapter(this, new ArrayList<FacebookEvent>());
        eventsList.setAdapter(adapter);*/
        manageLoginAction();
        if (FacebookGraphManager.isLogged()) {
            validateAndPerformFind();
        }

//        dbManager = new GiveMeEventDbManager(this);
//        dbManager.addorReplaceTraceCategory("23123123");
//        dbManager.getAllTraceCategories();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validateAndPerformFind() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, GiveMeEventUtils.ACCESS_FINE_LOCATION_CODE);
        } else {
            SharedPreferences prefs = GiveMeEventUtils.getPreferences(this);
            String lastLoc = GiveMeEventUtils.getPreferences(this).getString(getString(R.string.last_location), null);
            if (locManager.isLocationEnabled() && locManager.getLastKnownLocation() != null) {
                //perform con estas coordenadas
                Location location = locManager.getLastKnownLocation();
                gsd.setLatitud(location.getLatitude());
                gsd.setLongitud(location.getLongitude());
                perform();
//                perform(loc.getLatitude(), loc.getLongitude());
            } else if (lastLoc != null && !lastLoc.isEmpty()) {
                //perform con estas coordenadas
                String[] aux = lastLoc.split(",");
                gsd.setLatitud(Double.parseDouble(aux[0]));
                gsd.setLongitud(Double.parseDouble(aux[1]));
                perform();
//                perform(, );
            } else {
//                locManager.isLocationEnabled()
//                locManager.getLocation(locListener);
                requestLocation();
            }
        }
    }

    public void performExternal(GraphSearchData graph) {
        if (graph != null) {
            if (graph.getLongitud() == 0) {
                graph.setLongitud(gsd.getLongitud());
            }
            if (graph.getLatitud() == 0) {
                graph.setLatitud(gsd.getLatitud());
            }
            gsd = graph;
            perform();
        }
    }

    private void perform() {
        final FacebookGraphManager graphManager = ((GiveMeEventApplication) getApplication()).getFacebookGraphManager();
        GiveMeEventUtils.setPreference(this, getString(R.string.last_location), gsd.getLatitud() + "," + gsd.getLongitud());
        if (asyncFindEvents != null && asyncFindEvents.getStatus() == AsyncTask.Status.RUNNING) {
            asyncFindEvents.cancel(true);
        }
        //adapter.removeAllEvents();
        myAdapter.removeAllEvents();
        asyncFindEvents = new AsyncTask<Void, List<FacebookEvent>, List<FacebookEvent>>() {
            @Override
            protected List<FacebookEvent> doInBackground(Void[] objects) {
                if (!this.isCancelled()) {


                    List<String> ids = graphManager.findPlacesId(gsd);
//                //adapter = new EventAdapter(MainActivity.this, new ArrayList<FacebookEvent>());
//                    myAdapter = new RecycleViewAdapter(new ArrayList<FacebookEvent>(), MainActivity.this);
                    Log.d("CANTIDAD", ids.size() + "");
                    try {
//                        for (int i = 0; i < a.size(); i++) {
//                            if(this.isCancelled()){
//                                break;
//                            }
//                            List<FacebookEvent> b = graphManager.findEventsOfPlace(a.get(i), sd);
//                            Log.d("PLACE", a.get(i));
//                            publishProgress(b);
//                        }

                        if (ids.size() <= 50) {
                            List<FacebookEvent> b = graphManager.findEvents(ids, gsd);
//                            Log.d("PLACE", a.get(i));
                            publishProgress(b);
                        } else {
                            int count = 0;
                            for (int i = 0; i < (ids.size() - 50); i += 50) {
//                                String idsTemp = ids.subList(i, i + 50).toString();
                                List<FacebookEvent> b = graphManager.findEvents(ids.subList(i, i + 50), gsd);
                                publishProgress(b);
                            }
                            if (ids.size() % 50 != 0) {
//                    Log.d("QUANTITY", ids.subList(50*(idsSize/50), idsSize).size()+"");
                                String idsTemp = ids.subList(50 * (ids.size() / 50), ids.size()).toString();
                                List<FacebookEvent> b = graphManager.findEvents(ids.subList(50 * (ids.size() / 50), ids.size()), gsd);
                                publishProgress(b);
                            }
                        }
//                    List<FacebookEvent> b = graphManager.findEventsOfPlace("169530886826952");
//                    publishProgress(b);
                        return null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return new ArrayList<>();
            }

            @Override
            protected void onProgressUpdate(List<FacebookEvent>[] values) {
                super.onProgressUpdate(values);
                List<FacebookEvent> a = values[0];
                Log.d("CANTIDADFULL", myAdapter.getEvents().size() + "");
                //adapter.addEvents(a);
                myAdapter.addEvents(a);
//                String text = a.size()+"\n";

//                for (FacebookEvent e: a) {
//                    text+=(e.getName()+"----"+e.getPlaceOwner().getName()+"---"+e.getStartTime()+"\n///////////////\n");
//                    Log.d("EVENTID", e.getId());
//                }
////                ((TextView)findViewById(R.id.textView)).setText(text);
//                Log.d("EXECTIME",(new Date().getTime()/1000)+"");
            }

            @Override
            protected void onPostExecute(List<FacebookEvent> events) {
//                super.onPostExecute(events);
//                adapter.setEvents(events);
//                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_location) {
            requestLocation();
        } else if (id == R.id.action_search) {
            openFilterSearch();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.action_search) {
            openFilterSearch();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            if (FacebookGraphManager.isLogged()) {
                GiveMeEventUtils.showYesNoDialog(this, getString(R.string.app_name), getString(R.string.fb_logout_msg),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (asyncFindEvents != null && asyncFindEvents.getStatus() == AsyncTask.Status.RUNNING) {
                                    asyncFindEvents.cancel(true);
                                }
                                LoginManager.getInstance().logOut();
                                manageLoginAction();
                            }
                        }, null);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GiveMeEventUtils.ACCESS_FINE_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    validateAndPerformFind();
                } else {
                    GiveMeEventUtils.showToast(this, getString(R.string.permission_denied));
                }
                break;
        }
    }

    private void openFilterSearch() {

        FilterSearchDialog fDialog = FilterSearchDialog.newInstance();
        Bundle b = new Bundle();
        b.putSerializable("search", gsd);
        fDialog.setArguments(b);
        fDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLargeLayout) {
            fDialog.show(fragmentManager, "dialog");
        } else {
//            FilterSearchDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.search_msg));
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(R.id.drawer_layout, fDialog)
                    .addToBackStack(null).commit();
        }
    }

    private void requestLocation() {
        if (locManager.isLocationEnabled()) {
            locManager.getLocation(locListener);
        } else {
            GiveMeEventUtils.showYesNoDialog(this, getString(R.string.app_name), getString(R.string.location_active_msg),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent locIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locIntent);
                        }
                    }, null);
        }
    }

    private void manageLoginAction() {

        if (FacebookGraphManager.isLogged()) {
            loginButton.setVisibility(View.GONE);
            navigationView.getMenu().findItem(R.id.nav_logout).setEnabled(true);
        } else {
            loginButton.setVisibility(View.VISIBLE);
            navigationView.getMenu().findItem(R.id.nav_logout).setEnabled(false);
            myAdapter.removeAllEvents();
        }

    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services are working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can relve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, 100);
            dialog.show();
        } else {
            Toast.makeText(this, "We can't make map request", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////
    private class CustomLoginFacebookCallback implements FacebookCallback<LoginResult> {

        private Context ctx;

        public CustomLoginFacebookCallback(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("SUCCESS", "AQUI");
            manageLoginAction();
            validateAndPerformFind();
        }

        @Override
        public void onCancel() {
            Log.d("CANCEL", "AQUI");
            manageLoginAction();
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("ERROR", error.getMessage());
            manageLoginAction();
        }
    }
}

package it.unical.givemeevents.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import it.unical.givemeevents.MainActivity;
import it.unical.givemeevents.MapActivity;
import it.unical.givemeevents.R;
import it.unical.givemeevents.adapter.PlacesAutoCompleteAdapter;
import it.unical.givemeevents.model.GraphSearchData;
import it.unical.givemeevents.model.PlaceInfo;
import it.unical.givemeevents.util.GiveMeEventUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Manuel on 9/2/2018.
 */

public class FilterSearchDialog extends DialogFragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener{

    private int distance;
    private Date sinceD;
    private Date untilD;
    private String[] categoriesAux;
    private List<String> categories = new ArrayList<String>();
    private EditText since;
    private EditText until;
    private Spinner spinner;
    private CheckBox checkBox;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private AutoCompleteTextView mSearchText;
    private PlacesAutoCompleteAdapter myAdapter;
    private GeoDataClient mGeoDataClient;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo myPlace;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168), new LatLng(71, 136));


    public static FilterSearchDialog newInstance() {
        FilterSearchDialog frag = new FilterSearchDialog();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
        return frag;
    }

    private void populateFields(GraphSearchData searchData) {
        if (searchData != null) {
            if (searchData.getSince() != null) {
                since.setText(GiveMeEventUtils.createStringfromDate(searchData.getSince(), "dd/MM/yyyy"));
                sinceD = searchData.getSince();
            }
            if (searchData.getUntil() != null) {
                until.setText(GiveMeEventUtils.createStringfromDate(searchData.getUntil(), "dd/MM/yyyy"));
                untilD = searchData.getUntil();
            }
            distance = searchData.getDistance();
            //////////////////////Distance/////////////////////
            switch (searchData.getDistance()) {
                case 500:
                    spinner.setSelection(0, true);
                    break;
                case 1000:
                    spinner.setSelection(1, true);
                    break;
                case 3000:
                    spinner.setSelection(1, true);
                    break;
                case 5000:
                    spinner.setSelection(2, true);
                    break;
                case 10000:
                    spinner.setSelection(3, true);
                    break;
                default:
                    spinner.setSelection(0, true);
            }
            ////////////////////Checkboxes//////////////////////
            categories = new ArrayList<>(Arrays.asList(searchData.getCategories()));
            List<String> cats = new ArrayList<>(Arrays.asList(categoriesAux));
            for (int i = 0; i < searchData.getCategories().length; i++) {
                manageCheckBoxes(cats.indexOf(searchData.getCategories()[i]));
            }

        }
    }

    private void manageCheckBoxes(int pos) {
        switch (pos) {
            case 0:
                checkBox.performClick();
                break;
            case 1:
                checkBox2.performClick();
                break;
            case 2:
                checkBox3.performClick();
                break;
            case 3:
                checkBox4.performClick();
                break;
            case 4:
                checkBox5.performClick();
                break;
            case 5:
                checkBox6.performClick();
                break;
            case 6:
                checkBox7.performClick();
                break;
            case 7:
                checkBox8.performClick();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);\
        final View view = inflater.inflate(R.layout.search_filter_dialog, container, false);
        since = view.findViewById(R.id.editTextSince);
        until = view.findViewById(R.id.editTextUntil);
        spinner = view.findViewById(R.id.spinnerDistance);
        categoriesAux = getActivity().getResources().getStringArray(R.array.fb_graph_field_categories);
        checkBox = view.findViewById(R.id.checkBox);
        checkBox2 = view.findViewById(R.id.checkBox2);
        checkBox3 = view.findViewById(R.id.checkBox3);
        checkBox4 = view.findViewById(R.id.checkBox4);
        checkBox5 = view.findViewById(R.id.checkBox5);
        checkBox6 = view.findViewById(R.id.checkBox6);
        checkBox7 = view.findViewById(R.id.checkBox7);
        checkBox8 = view.findViewById(R.id.checkBox8);
        mSearchText = view.findViewById(R.id.input_search);

        Toolbar toolbar = view.findViewById(R.id.toolbar_dialog_search);
        toolbar.setTitle(getActivity().getString(R.string.search_msg));
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ActionBar actionbar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if(actionbar!=null){
//            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setHomeButtonEnabled(true);
//            actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
//        }
        GraphSearchData searchData = (GraphSearchData) getArguments().getSerializable("search");
        populateFields(searchData);
        toolbar.inflateMenu(R.menu.search_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.id_find_menu) {
                    Log.d("CLICKMENU", item.getItemId() + "");
                    GraphSearchData gsd = new GraphSearchData();
                    gsd.setDistance(distance);
                    gsd.setSince(sinceD);
                    gsd.setUntil(untilD);
                    if (!categories.isEmpty()) {
                        gsd.setCategories(categories.toArray(new String[categories.size()]));
                    } else {
                        gsd.setCategories(categoriesAux);
                    }
                    ((MainActivity) getActivity()).performExternal(gsd);
                    dismiss();
                }
                return false;
            }
        });
        setHasOptionsMenu(true);
        checkBox.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);

        ((Spinner) view.findViewById(R.id.spinnerDistance)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        distance = 500;
                        break;
                    case 1:
                        distance = 1000;
                        break;
                    case 2:
                        distance = 3000;
                        break;
                    case 3:
                        distance = 5000;
                        break;
                    case 4:
                        distance = 10000;
                        break;
                    default:
                        distance = 500;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        since.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog picker = GiveMeEventUtils.showDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar cal = new GregorianCalendar(year, month, date);
                        since.setText(GiveMeEventUtils.createStringfromDate(cal.getTime(), "dd/MM/yyyy"));
                        sinceD = cal.getTime();
                        ////IF IS GREATER THAN UNTIL, RESET UNTIL////
                        String untilDate = until.getText().toString();
                        if (untilDate != null && !untilDate.isEmpty()) {
                            Calendar uDate = new GregorianCalendar();
                            uDate.setTime(GiveMeEventUtils.createDateFromString(untilDate, "dd/MM/yyyy"));
                            if (uDate.compareTo(cal) < 0) {
                                until.setText("");
                            }
                        }
                    }
                });
                if (picker != null) {
                    picker.getDatePicker().setMinDate(new Date().getTime());
                }
            }
        });
        until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog picker = GiveMeEventUtils.showDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar cal = new GregorianCalendar(year, month, date, 23, 59);
                        until.setText(GiveMeEventUtils.createStringfromDate(cal.getTime(), "dd/MM/yyyy"));
                        untilD = cal.getTime();
                    }
                });
                if (picker != null) {
                    Calendar cal = new GregorianCalendar();
                    String sDate = since.getText().toString();

                    if (sDate != null && !sDate.isEmpty()) {
                        cal.setTime(GiveMeEventUtils.createDateFromString(sDate, "dd/MM/yyyy"));
                    }

                    cal.add(Calendar.DATE, 1);
                    picker.getDatePicker().setMinDate(cal.getTime().getTime());
                }
            }
        });

        myAdapter = new PlacesAutoCompleteAdapter(getContext(), mGeoDataClient, LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(myAdapter);

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity() , this)
                .build();
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);

        myAdapter = new PlacesAutoCompleteAdapter(getContext(), mGeoDataClient, LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(myAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    //execute Search Method
                    //geoLocate();
                }
                return false;
            }
        });

        return view;
    }

    public void onCheckBoxClicked(View check) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        View view = getActivity().getLayoutInflater().inflate(R.layout.search_filter_dialog, null);
//
//        SeekBar seek = view.findViewById(R.id.seekBarDistance);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            seek.setMin(500);
//        }
//        final TextView distanceText = view.findViewById(R.id.textViewDistanceValue);
//        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                distanceText.setText(i + "mts");
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });
//
//
//        builder.setView(view);
//        builder.setTitle(R.string.search_msg);
//        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // FIRE ZE MISSILES!
//            }
//        }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User cancelled the dialog
//            }
//        });
//        // Create the AlertDialog object and return it
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void addOrRemove(boolean checked, int index) {
        if (checked) {
            categories.add(categoriesAux[index]);
        } else {
            categories.remove(categoriesAux[index]);
        }
    }


    @Override
    public void onClick(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox:
                addOrRemove(checked, 0);
                break;
            case R.id.checkBox2:
                addOrRemove(checked, 1);
                break;
            case R.id.checkBox3:
                addOrRemove(checked, 2);
                break;
            case R.id.checkBox4:
                addOrRemove(checked, 3);
                break;
            case R.id.checkBox5:
                addOrRemove(checked, 4);
                break;
            case R.id.checkBox6:
                addOrRemove(checked, 5);
                break;
            case R.id.checkBox7:
                addOrRemove(checked, 6);
                break;
            case R.id.checkBox8:
                addOrRemove(checked, 7);
                break;
        }

    }


    /*public void HideSoftKeyboard(View view){
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager myKeyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        myKeyboard.hideSoftInputFromWindow(view.getWindowToken(),0);
    }*/

    //------------------------------

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //HideSoftKeyboard(mSearchText);
            final AutocompletePrediction item = myAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallBack);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallBack = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
               // Log.d(TAG, "onResult: Place Query did not completed sucessfully:" + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);
            try{
                myPlace = new PlaceInfo();
                myPlace.setAddress(place.getAddress().toString());

                myPlace.setCoordinates(place.getLatLng());
                myPlace.setId(place.getId());
                myPlace.setName(place.getName().toString());
                myPlace.setPhoneNumber(place.getPhoneNumber().toString());
                myPlace.setRating(place.getRating());
                myPlace.setWebSite(place.getWebsiteUri());
               // Log.d(TAG, "OnResult: place:" + myPlace.toString());
                Toast.makeText(getContext(), myPlace.toString(), Toast.LENGTH_LONG).show();
            }catch (NullPointerException e){
                //Log.e(TAG, "OnResult: NullPointerEception:" + e.getMessage());
            }

            places.release();
        }
    };
}

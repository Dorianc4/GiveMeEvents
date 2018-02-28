package it.unical.givemeevents.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import it.unical.givemeevents.R;
import it.unical.givemeevents.adapter.FavoritesAdapter;
import it.unical.givemeevents.adapter.PlacesAutoCompleteAdapter;
import it.unical.givemeevents.model.EventPlace;
import it.unical.givemeevents.model.FacebookEvent;
import it.unical.givemeevents.model.GraphSearchData;
import it.unical.givemeevents.model.PlaceInfo;
import it.unical.givemeevents.util.GiveMeEventUtils;

/**
 * Created by Manuel on 9/2/2018.
 */

public class FavoritesDialog extends DialogFragment {

    private ListView favoritesListView;
    private FavoritesAdapter adapter;


    public static FavoritesDialog newInstance() {
        FavoritesDialog frag = new FavoritesDialog();
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);\
        final View view = inflater.inflate(R.layout.favorites_dialog, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_dialog_favorites);
        toolbar.setTitle(getActivity().getString(R.string.favorites_msg));

        toolbar.inflateMenu(R.menu.favorite_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.ok_favorite_menu) {
//                    dismiss();
                    getActivity().onBackPressed();
                }
                return false;
            }
        });
        setHasOptionsMenu(true);
        List<EventPlace> places = (List<EventPlace>) getArguments().getSerializable("favorites");
        favoritesListView = view.findViewById(R.id.favoritesListView);
        adapter = new FavoritesAdapter(getActivity(), places);
        favoritesListView.setAdapter(adapter);
        return view;
    }

    public void onCheckBoxClicked(View check) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}

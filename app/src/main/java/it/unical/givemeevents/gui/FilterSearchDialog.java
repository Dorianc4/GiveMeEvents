package it.unical.givemeevents.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.unical.givemeevents.R;
import it.unical.givemeevents.util.GiveMeEventUtils;

/**
 * Created by Manuel on 9/2/2018.
 */

public class FilterSearchDialog extends DialogFragment {

    public static FilterSearchDialog newInstance() {
        FilterSearchDialog frag = new FilterSearchDialog();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);\
        View view = inflater.inflate(R.layout.search_filter_dialog, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar_dialog_search);
        toolbar.setTitle(getActivity().getString(R.string.search_msg));
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ActionBar actionbar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if(actionbar!=null){
//            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setHomeButtonEnabled(true);
//            actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
//        }
        toolbar.inflateMenu(R.menu.search_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.id_find_menu) {
                    Log.d("CLICKMENU", item.getItemId() + "");
                    dismiss();
                }
                return false;
            }
        });
        setHasOptionsMenu(true);

        final EditText since = view.findViewById(R.id.editTextSince);
        final EditText until = view.findViewById(R.id.editTextUntil);
        since.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog picker = GiveMeEventUtils.showDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar cal = new GregorianCalendar(year, month, date);
                        since.setText(GiveMeEventUtils.createStringfromDate(cal.getTime(), "dd/MM/yyyy"));
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
                        Calendar cal = new GregorianCalendar(year, month, date);
                        until.setText(GiveMeEventUtils.createStringfromDate(cal.getTime(), "dd/MM/yyyy"));
                    }
                });
                if (picker != null) {
                    Calendar cal = new GregorianCalendar();
                    cal.add(Calendar.DATE, 1);
                    picker.getDatePicker().setMinDate(cal.getTime().getTime());
                }
            }
        });

        return view;
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


}

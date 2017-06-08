package com.example.sargiskh.expensecalculation.dialogFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.example.sargiskh.expensecalculation.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private String sum;
    private Date date;
    private String description;

    public DatePickerDialogFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.sum = bundle.getString("sum");
        String dateStr = bundle.getString("date");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.description = bundle.getString("description");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDateSetListener = (DatePickerDialog.OnDateSetListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DatePickerDialog.OnDateSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDateSetListener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Dialog dialog = new DatePickerDialog((Context)onDateSetListener, onDateSetListener, year, month, day);
        return dialog;
    }
}

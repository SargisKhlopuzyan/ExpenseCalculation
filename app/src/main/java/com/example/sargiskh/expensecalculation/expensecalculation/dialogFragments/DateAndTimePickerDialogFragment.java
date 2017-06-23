package com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.sargiskh.expensecalculation.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class DateAndTimePickerDialogFragment extends DialogFragment {

    public interface DateAndTimePickerDialogFragmentListener {
        void onDateAndTimeSet(int year, int month, int dayOfMonth, int hourOfDay, int minute);
    }
    private DateAndTimePickerDialogFragmentListener listener;

    private DatePicker datePickerCalendar;
    private DatePicker datePickerSpinner;
    private TimePicker timePicker;
    private View dateAndTimeSpinnerViewContainer;
    private Button calendarButton;
    private Button cancelButton;
    private Button okButton;

    private Date date;

    public DateAndTimePickerDialogFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        String dateStr = bundle.getString("date");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.date_and_time_picker_dialog_layout, container, false);

        datePickerCalendar = (DatePicker)view.findViewById(R.id.datePickerCalendar);
        datePickerSpinner = (DatePicker)view.findViewById(R.id.datePickerSpinner);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        calendarButton = (Button) view.findViewById(R.id.calendarButton);
        dateAndTimeSpinnerViewContainer = view.findViewById(R.id.dateAndTimeSpinnerViewContainer);
        cancelButton = (Button)view.findViewById(R.id.cancelButton);
        okButton = (Button)view.findViewById(R.id.okButton);


        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datePickerCalendar.updateDate(year, month, day);
        datePickerSpinner.updateDate(year, month, day);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hourOfDay);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hourOfDay);
            timePicker.setCurrentMinute(minute);
        }

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calendarButton.getText().toString().equalsIgnoreCase("Calendar")) {

                    int year, month, dayOfMonth;
                    year = datePickerSpinner.getYear();
                    month = datePickerSpinner.getMonth();
                    dayOfMonth = datePickerSpinner.getDayOfMonth();
                    datePickerCalendar.updateDate(year, month, dayOfMonth);

                    datePickerCalendar.setVisibility(View.VISIBLE);
                    dateAndTimeSpinnerViewContainer.setVisibility(View.GONE);
                    calendarButton.setText("Detail");
                } else {

                    int year, month, dayOfMonth;
                    year = datePickerCalendar.getYear();
                    month = datePickerCalendar.getMonth();
                    dayOfMonth = datePickerCalendar.getDayOfMonth();

                    datePickerSpinner.updateDate(year, month, dayOfMonth);

                    calendarButton.setText("Calendar");
                    datePickerCalendar.setVisibility(View.INVISIBLE);
                    dateAndTimeSpinnerViewContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year, month, dayOfMonth, hourOfDay, minute;

                if (calendarButton.getText().toString().equalsIgnoreCase("Calendar")) {
                    year = datePickerCalendar.getYear();
                    month = datePickerCalendar.getMonth();
                    dayOfMonth = datePickerCalendar.getDayOfMonth();
                } else {
                    year = datePickerSpinner.getYear();
                    month = datePickerSpinner.getMonth();
                    dayOfMonth = datePickerSpinner.getDayOfMonth();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hourOfDay = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hourOfDay = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                Date tempDate = calendar.getTime();

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String strDate = dateFormat.format(tempDate);

                Log.e("DATA_PICKER", "DATE: " + strDate);

                if (listener != null) {
                    listener.onDateAndTimeSet(year, month, dayOfMonth, hourOfDay, minute);
                } else {
                    throw new ClassCastException(getActivity().toString() + " must implement DateAndTimePickerDialogFragment.DateAndTimePickerDialogFragmentListener");
                }
                dismiss();
            }
        });

        return view;
    }

    public void setDateAndTimePickerDialogFragmentListener(DateAndTimePickerDialogFragmentListener _listener) {
        listener = _listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            listener = (DatePickerDialog.OnDateSetListener)context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement DatePickerDialog.OnDateSetListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}

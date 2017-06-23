package com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sargiskh.expensecalculation.MainActivity;
import com.example.sargiskh.expensecalculation.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class ExpenseCalculationEditDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface OnDataChangeListener {
        void onDataChanged(int singleNotePosition, long id, long groupId, boolean isExpanse, double sum, String date, String currency, String description);
    }
    private OnDataChangeListener onDataChangeListener;


    private EditText editTextSum;
    private CheckBox isExpanseCheckBox;
    private Spinner currencySpinner;
    private Button buttonDate;
    private EditText editTextDescription;

    private Button buttonSave;
    private Button buttonClose;


    private Date tempDate;

    private int singleNotePosition = 0;
    private long id = 0;
    private long groupId = 0;
    private boolean isExpanse = true;
    private double sum = 0;
    private String dateString = "";
    private String currency = "";
    private String description = "";

    public ExpenseCalculationEditDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllowEnterTransitionOverlap(true);

        Bundle bundle = getArguments();

        singleNotePosition = bundle.getInt("singleNotePosition");
        id = bundle.getLong("id");
        groupId = bundle.getLong("groupId");
        isExpanse = bundle.getBoolean("isExpanse");
        sum = bundle.getDouble("sum");
        dateString = bundle.getString("date");
        currency = bundle.getString("currency");
        description = bundle.getString("description");

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            tempDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        setCancelable(true);

        // Create our view
        View view = inflater.inflate(R.layout.expense_calculation_edit_dialog_fragment_layout, container, true);

        editTextSum = (EditText) view.findViewById(R.id.editTextSum);
        isExpanseCheckBox = (CheckBox)view.findViewById(R.id.isExpanseCheckBox);
        currencySpinner = (Spinner)view.findViewById(R.id.currencySpinner);
        buttonDate = (Button) view.findViewById(R.id.buttonDate);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonClose = (Button) view.findViewById(R.id.buttonCancel);

        SetViews();

        // Spinner Drop down elements
        List<String> currencies = new ArrayList<>();
        currencies.add(getContext().getString(R.string.dram));
        currencies.add(getContext().getString(R.string.dollar));
        currencies.add(getContext().getString(R.string.euro));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, currencies);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        currencySpinner.setAdapter(dataAdapter);

        SetupClickListeners();

        return view;
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        onDataChangeListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            onDataChangeListener = (OnCurrencyListener)context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement DatePickerDialog.OnDateSetListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDataChangeListener = null;
    }

    private void SetupClickListeners() {
        buttonDate.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
    }

    private void SetViews() {
        editTextSum.setText("" + sum);
        isExpanseCheckBox.setChecked(isExpanse);
        editTextDescription.setText(description);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String stringDate = dateFormat.format(tempDate);
        buttonDate.setText(stringDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String newDateString = dateFormat.format(tempDate);

                sum = Float.parseFloat(editTextSum.getText().toString());
                currency = currencySpinner.getItemAtPosition(currencySpinner.getSelectedItemPosition()).toString();

                onDataChangeListener.onDataChanged(singleNotePosition, id, groupId, isExpanseCheckBox.isChecked(), sum, newDateString, currency, editTextDescription.getText().toString());
                dismiss();
                break;
            case R.id.buttonCancel:
                dismiss();
                break;
            case R.id.buttonDate:
                dateButtonClicked();
                break;
        }
    }

    private void dateButtonClicked() {
        DateAndTimePickerDialogFragment datePicker = new DateAndTimePickerDialogFragment();
        datePicker.setDateAndTimePickerDialogFragmentListener(((MainActivity)getActivity()).getECFragment());
        Bundle bundle = new Bundle();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = format.format(tempDate);
        bundle.putString("date", "" + strDate);
        datePicker.setArguments(bundle);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        datePicker.show(ft, "DatePicker");
    }

    public void onDateSet(int year, int month, int dayOfMonth, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        tempDate = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(tempDate);

        buttonDate.setText(strDate);
    }
}

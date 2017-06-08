package com.example.sargiskh.expensecalculation.dialogFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.sargiskh.expensecalculation.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class ExpenseCalculationEditDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface OnDataChangeListener {

        void onDataChanged(int position, String sum, String dateString, String description);
    }
    private OnDataChangeListener onDataChangeListener;

    private Button buttonSave;

    private Button buttonClose;
    private Button buttonDate;
    private EditText editTextSum;
    private EditText editTextDescription;

    private String sum;
    private Date tempDate;
    private String description;
    private int position;

    public ExpenseCalculationEditDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllowEnterTransitionOverlap(true);

        Bundle bundle = getArguments();
        this.sum = bundle.getString("sum");
        String dateStr = bundle.getString("date");

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try {
            tempDate = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.description = bundle.getString("description");
        this.position = bundle.getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        setCancelable(true);

        // Create our view
        View view = inflater.inflate(R.layout.expense_calculation_edit_dialog_fragment_layout, container, true);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonClose = (Button) view.findViewById(R.id.buttonCancel);
        buttonDate = (Button) view.findViewById(R.id.buttonDate);
        editTextSum = (EditText) view.findViewById(R.id.editTextSum);
        editTextDescription = (EditText) view.findViewById(R.id.editTextDescription);

        SetViews();

        SetupClickListeners();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onDataChangeListener = (OnDataChangeListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DatePickerDialog.OnDateSetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDataChangeListener = null;
    }

    private void SetupClickListeners() {
        buttonSave.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
    }

    private void SetViews() {
        editTextSum.setText(sum);
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
                String stringDate = dateFormat.format(tempDate);
                onDataChangeListener.onDataChanged(position, editTextSum.getText().toString(), stringDate, editTextDescription.getText().toString());
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
        DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sum", "" + sum);

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = format.format(tempDate);
        bundle.putString("date", "" + strDate);

        bundle.putString("description", "" + description);
        datePicker.setArguments(bundle);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        datePicker.show(ft, "DatePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        tempDate = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(tempDate);

        buttonDate.setText(strDate);
    }
}

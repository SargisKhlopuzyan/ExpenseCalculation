package com.example.sargiskh.expensecalculation.currency.dialogFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.sargiskh.expensecalculation.R;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class CurrencyDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface OnCurrencyListener {
        void onNewCurrencyCreated(boolean isMainCurrency, boolean isVisible, String fullName, String shortName, String simbole, double currencyRate);
    }
    private OnCurrencyListener onCurrencyListener;


    private RadioButton radioButton;
    private CheckBox checkBox;
    private EditText fullNameEditText;
    private EditText shortNameEditText;
    private EditText simboleEditText;
    private EditText currencyRateEditText;
    private Button okButton;;
    private Button cancelButton;;

    private boolean isMainCurrency = false;
    private boolean isVisible = false;
    private String fullName = "";
    private String shortName = "";
    private String simbole = "";
    private double currencyRate = 0;

    public CurrencyDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAllowEnterTransitionOverlap(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        setCancelable(true);

        // Create our view
        View view = inflater.inflate(R.layout.currency_dialog_fragment_layout, container, true);

        radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        checkBox = (CheckBox)view.findViewById(R.id.checkBox);
        fullNameEditText = (EditText) view.findViewById(R.id.fullNameEditText);
        shortNameEditText = (EditText) view.findViewById(R.id.shortNameEditText);
        simboleEditText = (EditText) view.findViewById(R.id.simboleEditText);
        currencyRateEditText = (EditText) view.findViewById(R.id.currencyRateEditText);

        okButton = (Button) view.findViewById(R.id.okButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        SetupClickListeners();
        return view;
    }

    public void setOnCurrencyListener(OnCurrencyListener listener) {
        onCurrencyListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onCurrencyListener = null;
    }

    private void SetupClickListeners() {
        radioButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private boolean checkStatus = false;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.okButton:
                isMainCurrency = radioButton.isChecked();
                isVisible = checkBox.isChecked();
                fullName = fullNameEditText.getText().toString();
                shortName = shortNameEditText.getText().toString();
                simbole = simboleEditText.getText().toString();

                String rate = currencyRateEditText.getText().toString();
                if (rate.isEmpty())
                    rate = "0";
                currencyRate = Float.parseFloat(rate);

                onCurrencyListener.onNewCurrencyCreated(isMainCurrency, isVisible, fullName, shortName, simbole, currencyRate);
                dismiss();
                break;
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.radioButton:
                checkStatus = !checkStatus;
                radioButton.setChecked(checkStatus);
                break;
        }
    }

}

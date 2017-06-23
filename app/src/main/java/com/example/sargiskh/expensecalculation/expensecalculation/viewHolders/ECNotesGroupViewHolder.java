package com.example.sargiskh.expensecalculation.expensecalculation.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.expensecalculation.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sargiskh on 5/30/2017.
 */

public class ECNotesGroupViewHolder {

    public View convertView;

    public TextView textViewDate;

    public TextView textViewSign;
    public TextView textViewCurrency;
    public TextView textViewSum;

    public View detailView;
    public ImageView imageViewEdit;
    public ImageView imageViewDelete;

    public TextView textViewDram;
    public TextView textViewDollar;
    public TextView textViewEuro;
    public TextView textViewDescription;


    public ECNotesGroupViewHolder(View convertView) {
        this.convertView = convertView;

        textViewDate = (TextView)convertView.findViewById(R.id.textViewDate);

        textViewSign = (TextView)convertView.findViewById(R.id.textViewSign);
        textViewCurrency = (TextView)convertView.findViewById(R.id.textViewCurrency);
        textViewSum = (TextView)convertView.findViewById(R.id.textViewSum);

        detailView = convertView.findViewById(R.id.detailView);
        imageViewEdit = (ImageView)convertView.findViewById(R.id.imageViewEdit);
        imageViewDelete = (ImageView)convertView.findViewById(R.id.imageViewDelete);

        textViewDram = (TextView)convertView.findViewById(R.id.textViewDram);
        textViewDollar = (TextView)convertView.findViewById(R.id.textViewDollar);
        textViewEuro = (TextView)convertView.findViewById(R.id.textViewEuro);
        textViewDescription = (TextView)convertView.findViewById(R.id.textViewDescription);
    }

    public void setData(final ECSingleNoteModel model, boolean _isEditMode) {

        if (_isEditMode) {
            textViewDate.setVisibility(View.VISIBLE);
            detailView.setVisibility(View.GONE);
            imageViewEdit.setVisibility(View.VISIBLE);
            imageViewDelete.setVisibility(View.VISIBLE);
        } else {
            textViewDate.setVisibility(View.VISIBLE);
            detailView.setVisibility(View.VISIBLE);
            imageViewEdit.setVisibility(View.INVISIBLE);
            imageViewDelete.setVisibility(View.INVISIBLE);
        }

        textViewSign.setText(model.isExpense() == true ? "-" : "+");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = model.getDate();
        String stringDate = dateFormat.format(date);

        textViewDate.setText(stringDate);

        textViewSum.setText(String.valueOf(model.getSum()));

        textViewDram.setVisibility(View.VISIBLE);
        textViewDollar.setVisibility(View.VISIBLE);
        textViewEuro.setVisibility(View.VISIBLE);

        switch (model.getCurrency()) {
            case Constants.DRAM:
                textViewCurrency.setText(textViewCurrency.getContext().getString(R.string.dramWidthColon));

                textViewDram.setText(textViewCurrency.getContext().getString(R.string.dramWidthColon) + String.valueOf(model.getSum()));
                textViewDram.setVisibility(View.GONE);
                textViewDollar.setText(textViewDollar.getContext().getString(R.string.dollarWidthColon) + String.valueOf(model.getSum()/Constants.DOLLAR_RATE));
                textViewEuro.setText(textViewEuro.getContext().getString(R.string.euroWidthColon) + String.valueOf(model.getSum()/Constants.EURO_RATE));

                break;
            case Constants.DOLLAR:
                textViewCurrency.setText(textViewCurrency.getContext().getString(R.string.dollarWidthColon));

                textViewDram.setText(textViewCurrency.getContext().getString(R.string.dramWidthColon) + String.valueOf(model.getSum()*Constants.DOLLAR_RATE));
                textViewDollar.setText(textViewDollar.getContext().getString(R.string.dollarWidthColon) + String.valueOf(model.getSum()));
                textViewDollar.setVisibility(View.GONE);
                textViewEuro.setText(textViewEuro.getContext().getString(R.string.euroWidthColon) + String.valueOf(model.getSum()*Constants.DOLLAR_RATE/Constants.EURO_RATE));
                break;
            case Constants.EURO:
                textViewCurrency.setText(textViewCurrency.getContext().getString(R.string.euroWidthColon));

                textViewDram.setText(textViewCurrency.getContext().getString(R.string.dramWidthColon) + String.valueOf(model.getSum()*Constants.EURO_RATE));
                textViewDollar.setText(textViewDollar.getContext().getString(R.string.dollarWidthColon) + String.valueOf(model.getSum()*Constants.EURO_RATE/Constants.DOLLAR_RATE));
                textViewEuro.setText(textViewEuro.getContext().getString(R.string.euroWidthColon) + String.valueOf(model.getSum()));
                textViewEuro.setVisibility(View.GONE);
                break;
        }

        if (model.getDescription().isEmpty()) {
            textViewDescription.setVisibility(View.GONE);
        } else {
            textViewDescription.setVisibility(View.VISIBLE);
            textViewDescription.setText(model.getDescription());
        }

    }
}

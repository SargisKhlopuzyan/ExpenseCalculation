package com.example.sargiskh.expensecalculation.currency.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.currency.models.CurrencyModel;

import java.util.ArrayList;

/**
 * Created by sargiskh on 5/2/2017.
 */

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter <CurrencyRecyclerViewAdapter.CurrencyViewHolder> {

    public interface OnCurrencyRecyclerViewAdapterListener {
        void onSetIsMainCurrency(long id, int position);
    }
    private OnCurrencyRecyclerViewAdapterListener onCurrencyRecyclerViewAdapterListener;

    private ArrayList<CurrencyModel> currencyModelsArrayList = new ArrayList<>();

    public CurrencyRecyclerViewAdapter(OnCurrencyRecyclerViewAdapterListener listener, ArrayList<CurrencyModel> _currencyModelsArrayList) {
        onCurrencyRecyclerViewAdapterListener = listener;
        currencyModelsArrayList = _currencyModelsArrayList;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context); ;
        View view = layoutInflater.inflate(R.layout.currency_recycler_view_item_layout, parent, false);

        CurrencyViewHolder currencyViewHolder = new CurrencyViewHolder(view);
        return currencyViewHolder;
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (currencyModelsArrayList == null)
            return 0;
        return currencyModelsArrayList.size();
    }

    public void updateData(ArrayList<CurrencyModel> _currencyModelsArrayList) {
        this.currencyModelsArrayList = _currencyModelsArrayList;
        notifyDataSetChanged();
    }

    public void itemInserted(CurrencyModel currencyModel) {
        notifyItemInserted(currencyModelsArrayList.size()-1);
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private RadioButton radioButton;
        private CheckBox checkBox;
        private EditText fullNameEditText;
        private EditText shortNameEditText;
        private EditText simboleEditText;
        private EditText currencyRateEditText;

        public CurrencyViewHolder(final View itemView) {
            super(itemView);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            fullNameEditText = (EditText) itemView.findViewById(R.id.fullNameEditText);
            shortNameEditText = (EditText) itemView.findViewById(R.id.shortNameEditText);
            simboleEditText = (EditText) itemView.findViewById(R.id.simboleEditText);
            currencyRateEditText = (EditText) itemView.findViewById(R.id.currencyRateEditText);

            setListeners();
        }

        private void setListeners() {
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (radioButton.isChecked() != currencyModelsArrayList.get(getAdapterPosition()).isMainCurrency()){
                        Log.e("XXXXXCCCC", "onCheckedChanged");
                        onCurrencyRecyclerViewAdapterListener.onSetIsMainCurrency(currencyModelsArrayList.get(getAdapterPosition()).getId(), getAdapterPosition());
                    }
                }
            });
        }

        public void bind(int position) {
            radioButton.setChecked(currencyModelsArrayList.get(position).isMainCurrency());
            checkBox.setChecked(currencyModelsArrayList.get(position).isVisible());
            fullNameEditText.setText(currencyModelsArrayList.get(position).getFullName());
            shortNameEditText.setText(currencyModelsArrayList.get(position).getShortName());
            simboleEditText.setText(currencyModelsArrayList.get(position).getSimbole());
            currencyRateEditText.setText("" + currencyModelsArrayList.get(position).getCurrencyRate());
        }
    }
}



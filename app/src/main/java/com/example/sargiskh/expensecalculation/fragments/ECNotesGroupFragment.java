package com.example.sargiskh.expensecalculation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.adapters.ECNotesGroupListViewAdapter;
import com.example.sargiskh.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ECNotesGroupFragment extends Fragment {

    public interface OnECDetailEditFragmentListener {
        void onAddNoteButtonClicked(int position);
        void onRemoveNotesGroupButtonClicked(int position);
    }
    private OnECDetailEditFragmentListener listener;


    private ArrayList<ECSingleNoteModel> singleNoteModelsArrayList = new ArrayList<>();


    private int position = -1;

    private TextView textViewIndex;
    private View overviewView;
    private View editModeView;

    private Button buttonEdit;
    private Button buttonRemoveNotesGroup;
    private Button buttonAdd;
    private Button buttonClose;

    private TextView textViewTotal;

    private ListView listView;
    private ECNotesGroupListViewAdapter adapter;

    public ECNotesGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ec_notes_group, container, false);

        textViewIndex = (TextView) view.findViewById(R.id.textViewIndex);

        overviewView = view.findViewById(R.id.overviewView);
        editModeView = view.findViewById(R.id.editModeView);

        buttonRemoveNotesGroup = (Button)view.findViewById(R.id.buttonRemoveNotesGroup);
        buttonEdit = (Button)view.findViewById(R.id.buttonEdit);
        buttonAdd = (Button)view.findViewById(R.id.buttonAddNotesGroup);
        buttonClose = (Button)view.findViewById(R.id.buttonClose);

        textViewTotal = (TextView) view.findViewById(R.id.textViewTotal);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setClickable(false);
        listView.setFastScrollEnabled(true);

        adapter = new ECNotesGroupListViewAdapter(getActivity(), singleNoteModelsArrayList, position);
        listView.setAdapter(adapter);

        setIndexTextView();

        editModeView.setVisibility(View.GONE);
        overviewView.setVisibility(View.VISIBLE);

        setTotalTextView();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModeView.setVisibility(View.VISIBLE);
                overviewView.setVisibility(View.GONE);
                adapter.updateEditMode(true);
            }
        });

        buttonRemoveNotesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemoveNotesGroupButtonClicked(position);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddNoteButtonClicked(position);
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModeView.setVisibility(View.GONE);
                overviewView.setVisibility(View.VISIBLE);
                adapter.updateEditMode(false);

            }
        });

        return view;
    }

    public void updateData(OnECDetailEditFragmentListener _onECDetailEditFragmentListener, ArrayList<ECSingleNoteModel> _singleNoteModelsArrayList, int _position) {
        listener = _onECDetailEditFragmentListener;
        singleNoteModelsArrayList = _singleNoteModelsArrayList;
        position = _position;

        if (listView != null) {
            setIndexTextView();
            adapter.updateData(singleNoteModelsArrayList, position);
            setTotalTextView();
        }
    }

    private void setIndexTextView() {
        if ( position >= singleNoteModelsArrayList.size() || position < 0) {
            textViewIndex.setText("");
            return;
        }
        textViewIndex.setText("" + position);
    }

    private void setTotalTextView() {

        if ( position >= singleNoteModelsArrayList.size() || position < 0) {
            textViewTotal.setText("xxxxx");
            return;
        }

        int sumDram = 0;
        int sumDollar = 0;
        int sumEuro = 0;

        for (ECSingleNoteModel singleNoteModel : singleNoteModelsArrayList) {
            switch (singleNoteModel.getCurrency()) {
                case Constants.DRAM:
                    sumDram += singleNoteModel.getSum();
                    break;
                case Constants.DOLLAR:
                    sumDollar += singleNoteModel.getSum();
                    break;
                case Constants.EURO:
                    sumEuro += singleNoteModel.getSum();
                    break;
            }
        }

        String total = "";
        if (sumDram != 0) {
            total += getResources().getString(R.string.dramWidthColon) + " " + sumDram;
        }
        if (sumDollar != 0) {
            if (!total.isEmpty()) {
                total += ", ";
            }
            total += getResources().getString(R.string.dollarWidthColon) + " " + sumDollar;
        }
        if (sumEuro != 0) {
            if (!total.isEmpty()) {
                total += ", ";
            }
            total += getResources().getString(R.string.euroWidthColon) + " " + sumEuro;
        }
        textViewTotal.setText(total);
    }


    @Override
    public void onDestroy() {
        listener = null;
        super.onDestroy();
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        adapter.onDateSet(view, year, month, dayOfMonth);

    }

    public void onDataChanged(int position, String sum, String dateString, String description) {

        singleNoteModelsArrayList.get(position).setSum(Float.valueOf(sum));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        singleNoteModelsArrayList.get(position).setDate(date);
        singleNoteModelsArrayList.get(position).setDescription(description);

        adapter.OnDataChanged(position, sum, dateString, description);

    }
}

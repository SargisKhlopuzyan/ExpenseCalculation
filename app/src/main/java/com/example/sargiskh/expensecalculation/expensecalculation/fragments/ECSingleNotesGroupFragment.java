package com.example.sargiskh.expensecalculation.expensecalculation.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.adapters.ECSingleNotesGroupListViewAdapter;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECNotesListModel;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.expensecalculation.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ECSingleNotesGroupFragment extends Fragment {

    public interface OnECSingleNotesGroupFragmentListener {
        void onAddSingleNoteButtonClicked();
        void onRemoveNotesGroupButtonClicked();
    }
    private OnECSingleNotesGroupFragmentListener listener;


    private ArrayList<ECSingleNoteModel> singleNoteModelsArrayList = new ArrayList<>();
    private ECNotesListModel notesListModel = new ECNotesListModel();

    private TextView textViewIndex;
    private View editModeContainerView;

    private Button buttonEditMode;

    private Button buttonRemoveNotesGroup;
    private Button buttonAdd;
    private Button buttonCloseMode;

    private TextView textViewTotal;

    private ListView listView;
    private ECSingleNotesGroupListViewAdapter adapter;

    public ECSingleNotesGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ec_notes_group, container, false);

        textViewIndex = (TextView) view.findViewById(R.id.textViewIndex);

        editModeContainerView = view.findViewById(R.id.editModeContainerView);

        buttonRemoveNotesGroup = (Button)view.findViewById(R.id.buttonRemoveNotesGroup);
        buttonEditMode = (Button)view.findViewById(R.id.buttonEdit);
        buttonAdd = (Button)view.findViewById(R.id.buttonAddNotesGroup);
        buttonCloseMode = (Button)view.findViewById(R.id.buttonClose);

        textViewTotal = (TextView) view.findViewById(R.id.textViewTotal);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setClickable(false);
        listView.setFastScrollEnabled(true);


        adapter = new ECSingleNotesGroupListViewAdapter(getActivity(), singleNoteModelsArrayList);
        listView.setAdapter(adapter);

        editModeContainerView.setVisibility(View.GONE);
        buttonEditMode.setVisibility(View.VISIBLE);

        setIndexTextView();
        setTotalTextView();

        buttonRemoveNotesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemoveNotesGroupButtonClicked();
                } else {
                    Log.e("XX_XX_XX_XX", "1: listener == null");
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddSingleNoteButtonClicked();
                } else {
                    Log.e("XX_XX_XX_XX", "2: listener == null");
                }
            }
        });

        buttonCloseMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        editModeContainerView.setVisibility(View.GONE);
                        buttonCloseMode.setVisibility(View.GONE);
                        buttonEditMode.setVisibility(View.VISIBLE);
                        adapter.updateEditMode(false);
                    }
                });
            }
        });

        buttonEditMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        buttonEditMode.setVisibility(View.GONE);
                        buttonCloseMode.setVisibility(View.VISIBLE);
                        editModeContainerView.setVisibility(View.VISIBLE);
                        adapter.updateEditMode(true);
                    }
                });
            }
        });

        return view;
    }

    public void updateData(OnECSingleNotesGroupFragmentListener _onECSingleNotesGroupFragmentListener, ArrayList<ECSingleNoteModel> _singleNoteModelsArrayList, ECNotesListModel _notesListModel) {

        listener = _onECSingleNotesGroupFragmentListener;
        notesListModel = _notesListModel;
        singleNoteModelsArrayList = _singleNoteModelsArrayList;

        if (listView != null) {
            setIndexTextView();
            setTotalTextView();
            adapter.updateData(singleNoteModelsArrayList);
        }
    }

    private void setIndexTextView() {
        textViewIndex.setText("" + notesListModel.getGroupName());
    }

    private void setTotalTextView() {

        int sumDram = 0;
        int sumDollar = 0;
        int sumEuro = 0;


        for (ECSingleNoteModel singleNoteModel : singleNoteModelsArrayList) {

            switch (singleNoteModel.getCurrency()) {
                case Constants.DRAM:
                    if (singleNoteModel.isExpense()) {
                        sumDram -= singleNoteModel.getSum();
                    } else {
                        sumDram += singleNoteModel.getSum();
                    }
                    break;
                case Constants.DOLLAR:
                    if (singleNoteModel.isExpense()) {
                        sumDollar -= singleNoteModel.getSum();
                    } else {
                        sumDollar += singleNoteModel.getSum();
                    }
                    break;
                case Constants.EURO:
                    if (singleNoteModel.isExpense()) {
                        sumEuro -= singleNoteModel.getSum();
                    } else {
                        sumEuro += singleNoteModel.getSum();
                    }
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

        if (sumDram == 0 && sumDollar == 0 && sumEuro == 0) {
            total = getResources().getString(R.string.dramWidthColon) + " " + 0;
        }

        textViewTotal.setText(total);
    }


    @Override
    public void onDestroy() {
        listener = null;
        Log.e("XXXXXCCC", "onDestroy()");
        super.onDestroy();
    }


    public void onDateSet(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        adapter.onDateSet(year, month, dayOfMonth, hourOfDay, minute);
    }

    public void onDataChanged(int singleNotePosition, long id, long groupId, boolean isExpanse, double sum, String dateString, String currency, String description) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        singleNoteModelsArrayList.get(singleNotePosition).setExpense(isExpanse);
        singleNoteModelsArrayList.get(singleNotePosition).setSum(sum);
        singleNoteModelsArrayList.get(singleNotePosition).setDate(date);
        singleNoteModelsArrayList.get(singleNotePosition).setCurrency(currency);
        singleNoteModelsArrayList.get(singleNotePosition).setDescription(description);

        adapter.OnDataChanged(singleNotePosition, id, groupId, isExpanse, sum, dateString, currency, description);

        setTotalTextView();
    }

    public void removeSingleNote(int singleNotePosition, long id) {
//        singleNoteModelsArrayList.remove(singleNotePosition);
        adapter.removeSingleNote(singleNotePosition, id);
        setTotalTextView();
    }
}

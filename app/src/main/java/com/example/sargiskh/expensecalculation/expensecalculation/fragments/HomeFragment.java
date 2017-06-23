package com.example.sargiskh.expensecalculation.expensecalculation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sargiskh.expensecalculation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    public interface MainFragmentListener {
        void expenseCalculationButtonClicked();
        void notesButtonClicked();
        void notificationButtonClicked();
    }
    private MainFragmentListener listener;

    private Button notesButton;
    private Button memoryButton;
    private Button notificationButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        notesButton = (Button)view.findViewById(R.id.notesListButton);
        memoryButton = (Button)view.findViewById(R.id.notesButton);
        notificationButton = (Button)view.findViewById(R.id.notificationButton);

        notesButton.setOnClickListener(this);
        memoryButton.setOnClickListener(this);
        notificationButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notesListButton:
                listener.expenseCalculationButtonClicked();
                break;
            case R.id.notesButton:
                listener.notesButtonClicked();
                break;
            case R.id.notificationButton:
                listener.notificationButtonClicked();
                break;
        }
    }

    public void setListener(MainFragmentListener _listener) {
        listener = _listener;
    }

    @Override
    public void onDestroy() {
        listener = null;
        super.onDestroy();
    }

}

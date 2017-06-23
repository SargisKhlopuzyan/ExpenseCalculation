package com.example.sargiskh.expensecalculation.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments.DateAndTimePickerDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    Button clickMeButton;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        clickMeButton = (Button)view.findViewById(R.id.clickMe);
        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimePickerDialogFragment dateAndTimePickerDialogFragment = new DateAndTimePickerDialogFragment();
                dateAndTimePickerDialogFragment.show(getFragmentManager(), "DateAndTimePickerDialogFragment");
            }
        });

        return view;
    }

}

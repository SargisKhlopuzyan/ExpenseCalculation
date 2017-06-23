package com.example.sargiskh.expensecalculation.notes.fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.notes.adapters.NotesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private Button buttonGoTo;

    private ListView listView;
    private NotesAdapter adapter;
    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        buttonGoTo = (Button) view.findViewById(R.id.buttonGoTo);
        buttonGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listView.smoothScrollToPosition(10,0);
                listView.smoothScrollToPositionFromTop(10,0);
            }
        });

        listView = (ListView) view.findViewById(R.id.listView);

        ArrayList<String> notes = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            notes.add(" note #" + i);
        }

        adapter = new NotesAdapter(getActivity(), notes);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(onScrollListener1);
        return view;
    }


    private boolean isScrollListenerEnabled = true;


    private AbsListView.OnScrollListener onScrollListener1 = new AbsListView.OnScrollListener() {

        private int previousFirstVisibleItem = 0;
        private long previousEventTime = 0;
        private double speed = 0;

        private int myScrollState = 0;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            myScrollState = scrollState;
            if (scrollState == 0) {
                isScrollListenerEnabled = true;
            }
            Log.d("DBG", "");
        }

        @Override
        public void onScroll(AbsListView view, final int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (previousFirstVisibleItem != firstVisibleItem){
                long currTime = System.currentTimeMillis();
                long timeToScrollOneElement = currTime - previousEventTime;
                speed = ((double)1/timeToScrollOneElement)*1000;

                previousFirstVisibleItem = firstVisibleItem;
                previousEventTime = currTime;

                Log.d("DBG", "State: " + myScrollState + ", Speed: " + speed);
                if (myScrollState == 2) {

                    if (speed < 5) {
                        Log.d("DBGG", "firstVisibleItem : " + firstVisibleItem);
                        listView.setOnScrollListener(onScrollListener2);

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPositionFromTop(firstVisibleItem + 1, 0);
                            }
                        });


                    }
                }
            }
        }
    };


    private AbsListView.OnScrollListener onScrollListener2 = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                listView.setOnScrollListener(onScrollListener1);
                isScrollListenerEnabled = true;
            }
        }

        @Override
        public void onScroll(AbsListView view, final int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

}

package com.example.sargiskh.expensecalculation.blot.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.currency.adapters.CurrencyRecyclerViewAdapter;
import com.example.sargiskh.expensecalculation.currency.database.CurrencyDataSource;
import com.example.sargiskh.expensecalculation.currency.dialogFragments.CurrencyDialogFragment;
import com.example.sargiskh.expensecalculation.currency.helpers.Converters;
import com.example.sargiskh.expensecalculation.currency.models.CurrencyModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlotFragment extends Fragment implements CurrencyDialogFragment.OnCurrencyListener, CurrencyRecyclerViewAdapter.OnCurrencyRecyclerViewAdapterListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private Button addCurrencyButton;

    private CurrencyDataSource dataSource;

    private ArrayList<CurrencyModel> currencyModelArrayList = new ArrayList<>();

    private CurrencyRecyclerViewAdapter adapter;

    private static boolean IS_DATA_LOADING = false;

    public BlotFragment() {
        // Required empty public constructor
    }

    private int mainCurrencyPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_currency, container, false);;
        findViews(view);

        adapter = new CurrencyRecyclerViewAdapter(this, currencyModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataSource = new CurrencyDataSource(getActivity());

        if (!IS_DATA_LOADING) {
            CurrencyAsyncTaskLoader currencyAsyncTaskLoader = new CurrencyAsyncTaskLoader(getActivity());
            currencyAsyncTaskLoader.startLoading();
        }

        addCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyDialogFragment currencyDialogFragment = new CurrencyDialogFragment();
                currencyDialogFragment.setOnCurrencyListener(CurrencyFragment.this);
                currencyDialogFragment.show(getFragmentManager(), "CurrencyDialogFragment");
            }
        });

        return view;
    }

    private void findViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        addCurrencyButton = (Button)view.findViewById(R.id.addCurrencyButton);
    }

    @Override
    public void onNewCurrencyCreated(boolean isMainCurrency, boolean isVisible, String fullName, String shortName, String simbole, double currencyRate) {

        dataSource.open();
        long id = dataSource.addCurrency(isMainCurrency, isVisible, fullName, shortName, simbole, currencyRate);
        if (isMainCurrency) {
            dataSource.setMainCurrency(id);
            currencyModelArrayList.get(mainCurrencyPosition).setMainCurrency(false);
            adapter.notifyItemChanged(mainCurrencyPosition);
            mainCurrencyPosition = currencyModelArrayList.size();
        } //TODO
        dataSource.close();

        CurrencyModel currencyModel = new CurrencyModel(id, isMainCurrency, isVisible, fullName, shortName, simbole, currencyRate);
        currencyModelArrayList.add(currencyModel);
        adapter.itemInserted(currencyModel);
    }

    @Override
    public void onSetIsMainCurrency(long id, int position) {
        dataSource.open();
        dataSource.setMainCurrency(id);
        dataSource.close();
        CurrencyAsyncTaskLoader currencyAsyncTaskLoader = new CurrencyAsyncTaskLoader(getActivity());
        currencyAsyncTaskLoader.startLoading();
    }

    //AsyncTaskLoader part
    private class CurrencyAsyncTaskLoader extends AsyncTaskLoader<Cursor> {

        public CurrencyAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            progressBar.setVisibility(View.VISIBLE);
            if (!IS_DATA_LOADING) {
                IS_DATA_LOADING = true;
                forceLoad();
            }
        }

        @Override
        public Cursor loadInBackground() {
            dataSource.open();
            Cursor cursor = dataSource.getAllCurrency();
            dataSource.close();
            return cursor;
        }

        @Override
        public void deliverResult(Cursor cursor) {
            super.deliverResult(cursor);
            progressBar.setVisibility(View.GONE);
            IS_DATA_LOADING = false;
            Converters.convertCursorToCurrencyModels(cursor, currencyModelArrayList);
            adapter.updateData(currencyModelArrayList);
            for (int i = 0; i < currencyModelArrayList.size(); i++) {
                if (currencyModelArrayList.get(i).isMainCurrency()) {
                    mainCurrencyPosition = i;
                    break;
                }
            }
            Log.e("XXXXXXX", "deliverResult Count: " + cursor.getCount());
        }
    }
}

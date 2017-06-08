package com.example.sargiskh.expensecalculation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.adapters.ECNotesListRecyclerViewAdapter;
import com.example.sargiskh.expensecalculation.helper.OnItemClickListener;
import com.example.sargiskh.expensecalculation.helper.OnStartDragListener;
import com.example.sargiskh.expensecalculation.helper.SimpleItemTouchHelperCallback;
import com.example.sargiskh.expensecalculation.models.ECNotesGroupModel;
import com.example.sargiskh.expensecalculation.models.ECNotesListModel;
import com.example.sargiskh.expensecalculation.models.ECSingleNoteModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ECFragment extends Fragment implements OnItemClickListener, OnStartDragListener, ECNotesGroupFragment.OnECDetailEditFragmentListener {

    private Fragment notesGroupFragment;

    private ItemTouchHelper mItemTouchHelper;

    private Button buttonAddNotesGroup;

    private RecyclerView notesListRecyclerView;
    private ECNotesListRecyclerViewAdapter adapter;

    private ArrayList<ECNotesListModel> notesListModelArrayList = new ArrayList<ECNotesListModel>();
    private ArrayList<ECNotesGroupModel> notesGroupModelArrayList = new ArrayList<ECNotesGroupModel>();

    private int noteIndex = -1;

    public ECFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_calculation, container, false);
        buttonAddNotesGroup = (Button) view.findViewById(R.id.buttonAddNotesGroup);
        notesListRecyclerView = (RecyclerView) view.findViewById(R.id.notesListRecyclerView);

        //RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notesListRecyclerView.setLayoutManager(linearLayoutManager);
        notesListRecyclerView.setHasFixedSize(true);
        notesListRecyclerView.setVerticalScrollBarEnabled(false);
        notesListRecyclerView.setHorizontalScrollBarEnabled(false);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(notesListRecyclerView);
        setNotesListModels();
        adapter = new ECNotesListRecyclerViewAdapter(getActivity(), linearLayoutManager, this, this, notesListModelArrayList);
        notesListRecyclerView.setAdapter(adapter);
        //

        setNotesGroupFragment(noteIndex);

        buttonAddNotesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteGroup();
            }
        });

        return view;
    }

    private void addNoteGroup() {

        if (noteIndex >=0 && noteIndex < notesListModelArrayList.size()) {
            notesGroupModelArrayList.get(noteIndex).getNotesListModel().setItemSelectedState(false);
        }

        ECNotesListModel notesListModel = new ECNotesListModel();
        notesListModel.setItemSelectedState(true);
        notesListModel.setItemPosition(0);
        notesListModel.setItemDescription("# " + (notesGroupModelArrayList.size()));

        ECNotesGroupModel notesGroupModel = new ECNotesGroupModel();
        notesGroupModel.setNotesListModel(notesListModel);

        ArrayList<ECSingleNoteModel> singleNoteModelsArrayList = new ArrayList<>();
        for (int k = 0; k < notesGroupModelArrayList.size(); k++) {
            ECSingleNoteModel model = new ECSingleNoteModel();
            model.setSum(k*1000);
            model.setDate(new Date());
            model.setDescription("this is a description for item NEW " + k);
            singleNoteModelsArrayList.add(model);
        }
        notesGroupModel.setSingleNoteModelsList(singleNoteModelsArrayList);

        notesGroupModelArrayList.add(notesGroupModel);

        noteIndex = notesGroupModelArrayList.size()-1;

        setNotesListModels();

        adapter.updateData(notesListModelArrayList, noteIndex);
        notesListRecyclerView.scrollToPosition(noteIndex);

        setNotesGroupFragment(noteIndex);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        setNotesGroupFragment(viewHolder.getAdapterPosition());
    }

    private void setNotesListModels() {
        notesListModelArrayList.clear();
        for (int i = 0; i < notesGroupModelArrayList.size(); i++) {
            notesListModelArrayList.add(notesGroupModelArrayList.get(i).getNotesListModel());
        }
    }
    
    private void setNotesGroupFragment(int _noteIndex) {

        Log.e("XXXXX", "_noteIndex: " + _noteIndex);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        notesGroupFragment = fragmentManager.findFragmentByTag("ECNotesGroupFragment");
        if (notesGroupFragment == null) {
            notesGroupFragment = new ECNotesGroupFragment();
            fragmentTransaction.replace(R.id.frameLayoutFragmentExpenseCalculation, notesGroupFragment, "ECNotesGroupFragment");
            fragmentTransaction.commit();
            Log.e("XXXXX", "1");
        } else {
            notesGroupFragment = fragmentManager.findFragmentByTag("ECNotesGroupFragment");
            Log.e("XXXXX", "2");
        }

        noteIndex = _noteIndex;
        if (noteIndex >= 0) {
            Log.e("XXXXX", "3");
            ((ECNotesGroupFragment)notesGroupFragment).updateData(this, notesGroupModelArrayList.get(noteIndex).getSingleNoteModelsList(), noteIndex);
        } else {
            ((ECNotesGroupFragment)notesGroupFragment).updateData(this, new ArrayList<ECSingleNoteModel>(), noteIndex);
        }
    }

    @Override
    public void onRemoveNotesGroupButtonClicked(int _position) {
        if (_position >= 0 && _position < notesGroupModelArrayList.size()) {
            notesGroupModelArrayList.remove(_position);
            noteIndex = _position;
        } else {
            noteIndex = -1;
        }

        if (noteIndex >= 0 && noteIndex >= notesGroupModelArrayList.size()) {
            noteIndex = notesGroupModelArrayList.size() - 1;
        }

        if (noteIndex >= 0 && noteIndex < notesGroupModelArrayList.size()) {
            notesGroupModelArrayList.get(noteIndex).getNotesListModel().setItemSelectedState(true);
        }

        setNotesListModels();
        adapter.updateData(notesListModelArrayList, noteIndex);
        setNotesGroupFragment(noteIndex);
    }

    @Override
    public void onAddNoteButtonClicked(int _position) {

        ECSingleNoteModel singleNoteModel = new ECSingleNoteModel();
        singleNoteModel.setSum(999999);
        singleNoteModel.setDate(new Date());
        singleNoteModel.setDescription("this is a description for item " + 999999);
        notesGroupModelArrayList.get(_position).getSingleNoteModelsList().add(singleNoteModel);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        notesGroupFragment = fragmentManager.findFragmentByTag("ECNotesGroupFragment");
        if (notesGroupFragment == null) {
            notesGroupFragment = new ECNotesGroupFragment();
            fragmentTransaction.replace(R.id.frameLayoutFragmentExpenseCalculation, notesGroupFragment, "ECNotesGroupFragment");
            fragmentTransaction.commit();
        } else {
            notesGroupFragment = fragmentManager.findFragmentByTag("ECNotesGroupFragment");
        }

        ((ECNotesGroupFragment) notesGroupFragment).updateData(this, notesGroupModelArrayList.get(noteIndex).getSingleNoteModelsList(), _position);
    }



    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        ((ECNotesGroupFragment)notesGroupFragment).onDateSet(view, year, month, dayOfMonth);
    }

    public void onDataChanged(int position, String sum, String dateString, String description) {
        ((ECNotesGroupFragment)notesGroupFragment).onDataChanged(position, sum, dateString, description);
    }

}

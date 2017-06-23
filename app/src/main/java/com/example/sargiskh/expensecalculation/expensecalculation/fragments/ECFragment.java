package com.example.sargiskh.expensecalculation.expensecalculation.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.adapters.ECSingleNotesGroupListViewAdapter;
import com.example.sargiskh.expensecalculation.expensecalculation.adapters.ECNotesListRecyclerViewAdapter;
import com.example.sargiskh.expensecalculation.expensecalculation.database.ECDataSource;
import com.example.sargiskh.expensecalculation.expensecalculation.database.ECSQLiteOpenHelper;
import com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments.DateAndTimePickerDialogFragment;
import com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments.ExpenseCalculationEditDialogFragment;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.OnItemClickListener;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.OnStartDragListener;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.SimpleItemTouchHelperCallback;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECNotesListModel;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECSingleNotesGroupModel;
import com.example.sargiskh.expensecalculation.expensecalculation.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ECFragment extends Fragment implements OnItemClickListener, OnStartDragListener,
        ECSingleNotesGroupFragment.OnECSingleNotesGroupFragmentListener,
        ECSingleNotesGroupListViewAdapter.OnECNotesGroupListViewAdapterListener,
        ExpenseCalculationEditDialogFragment.OnDataChangeListener,
        DateAndTimePickerDialogFragment.DateAndTimePickerDialogFragmentListener{

    //DB
    private ECDataSource dataSource;
    //

    private ItemTouchHelper mItemTouchHelper;

    private Button buttonAddNotesGroup;

    private RecyclerView notesListRecyclerView;
    private ECNotesListRecyclerViewAdapter adapter;

    private ArrayList<ECNotesListModel> notesListModelArrayList = new ArrayList<ECNotesListModel>();
    private ArrayList<ECSingleNotesGroupModel> notesGroupModelArrayList = new ArrayList<ECSingleNotesGroupModel>();

    private int selectedNotesListIndex = -1;

    public ECFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ec, container, false);

        dataSource = new ECDataSource(getActivity());

        buttonAddNotesGroup = (Button) view.findViewById(R.id.buttonAddNotesGroup);
        notesListRecyclerView = (RecyclerView) view.findViewById(R.id.notesListRecyclerView);
        buttonAddNotesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryToAddNotesList();
            }
        });

        queryToGetAllNotesList();
        queryToGetAllNotesGroup();

        //RecyclerView
        setNotesListView();

        //Notes Group Fragment
        setSingleNotesGroupFragment();

        return view;
    }


    private static int number = 0;

    private void queryToAddNotesList() {

        String groupName = "Group " + number;
        int priority = 1;
        ++number;

        dataSource.open();
        long id = dataSource.addNotesList(groupName, priority);
        dataSource.close();

        Log.e("ERROR_R", "add id: " + id);

        if (id >= 0) {
            ECNotesListModel notesListModel = new ECNotesListModel(id, groupName, priority);
            notesListModelArrayList.add(notesListModel);

            selectedNotesListIndex = notesListModelArrayList.size() - 1;

            adapter.updateData(notesListModelArrayList, selectedNotesListIndex);
            notesListRecyclerView.scrollToPosition(selectedNotesListIndex);

            ECSingleNotesGroupModel singleNotesGroupModel = new ECSingleNotesGroupModel();
            singleNotesGroupModel.setGroupName(groupName);
            notesGroupModelArrayList.add(singleNotesGroupModel);

            selectedNotesListIndex = selectedNotesListIndex < 0 ? 0 : notesGroupModelArrayList.size() -1;

            setSingleNotesGroupFragment();
        }
    }

    private void queryToGetAllNotesList() {
        dataSource.open();
        Cursor cursor = dataSource.getAllNotesLists();
        dataSource.close();
        cursor.moveToFirst();

        notesListModelArrayList.clear();

        if (cursor.moveToFirst()){
            do{
                long id = cursor.getInt(cursor.getColumnIndex(ECSQLiteOpenHelper.ID));
                String groupName = cursor.getString(cursor.getColumnIndex(ECSQLiteOpenHelper.GROUP_NAME));
                int priority = cursor.getInt(cursor.getColumnIndex(ECSQLiteOpenHelper.PRIORITY));

                ECNotesListModel notesListModel = new ECNotesListModel(id, groupName, priority);

                notesListModelArrayList.add(notesListModel);
            }while(cursor.moveToNext());
        }

        if (notesListModelArrayList.size() > 0) {
            selectedNotesListIndex = 0;
        } else {
            selectedNotesListIndex = -1;
        }
    }


    private ECSingleNotesGroupModel queryToGetAllSingleNotesByGroupName(String _groupId) {
        dataSource.open();
        String[] selectionArg = {_groupId};
        Cursor cursor = dataSource.getAllSingleNotesBySelection(ECSQLiteOpenHelper.GROUP_ID + " =?", selectionArg);
        dataSource.close();

        ECSingleNotesGroupModel singleNotesGroupModel = new ECSingleNotesGroupModel();

        if (cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndex(ECSQLiteOpenHelper.ID));
                long groupId = cursor.getInt(cursor.getColumnIndex(ECSQLiteOpenHelper.GROUP_ID));
                int expenseSign = cursor.getInt(cursor.getColumnIndex(ECSQLiteOpenHelper.IS_EXPENSE));
                boolean isExpense = expenseSign == 0 ? false : true;
                double sum = cursor.getFloat(cursor.getColumnIndex(ECSQLiteOpenHelper.SUM));
                String dateString = cursor.getString(cursor.getColumnIndex(ECSQLiteOpenHelper.DATE_AS_STRING));
                String currency = cursor.getString(cursor.getColumnIndex(ECSQLiteOpenHelper.CURRENCY));
                String description = cursor.getString(cursor.getColumnIndex(ECSQLiteOpenHelper.DESCRIPTION));

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                Date date = null;
                try {
                    date = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ECSingleNoteModel singleNoteModel = new ECSingleNoteModel();
                singleNoteModel.setId(id);
                singleNoteModel.setGroupId(groupId);
                singleNoteModel.setExpense(isExpense);
                singleNoteModel.setSum(sum);
                singleNoteModel.setDate(date);
                singleNoteModel.setCurrency(currency);
                singleNoteModel.setDescription(description);

                singleNotesGroupModel.getSingleNoteModelsList().add(singleNoteModel);
            } while(cursor.moveToNext());
        }

        singleNotesGroupModel.setGroupName(_groupId);

        return singleNotesGroupModel;
    }

    private void queryToGetAllNotesGroup() {
        for (int i = 0; i < notesListModelArrayList.size(); i++) {
            ECSingleNotesGroupModel singleNotesGroupModel = queryToGetAllSingleNotesByGroupName("" + notesListModelArrayList.get(i).getGroupId());
            notesGroupModelArrayList.add(singleNotesGroupModel);
        }
    }

    private void setNotesListView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notesListRecyclerView.setLayoutManager(linearLayoutManager);
        notesListRecyclerView.setHasFixedSize(true);
        notesListRecyclerView.setVerticalScrollBarEnabled(false);
        notesListRecyclerView.setHorizontalScrollBarEnabled(false);

        adapter = new ECNotesListRecyclerViewAdapter(getActivity(), linearLayoutManager, this, this, notesListModelArrayList);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(notesListRecyclerView);

        notesListRecyclerView.setAdapter(adapter);
    }

    private void setSingleNotesGroupFragment() {
        Log.e("ERROR_R", "setSingleNotesGroupFragment() : selectedNotesListIndex: " + selectedNotesListIndex);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment notesGroupFragment = fragmentManager.findFragmentById(R.id.frameLayoutFragmentExpenseCalculation);

        if (selectedNotesListIndex < 0 && notesGroupFragment != null && notesGroupFragment instanceof ECSingleNotesGroupFragment) {
            Log.e("ERROR_R", "1");
            fragmentTransaction.remove(notesGroupFragment);
            fragmentTransaction.commit();
            return;
        } else if (selectedNotesListIndex >= 0 && (notesGroupFragment == null || !(notesGroupFragment instanceof ECSingleNotesGroupFragment))) {
            Log.e("ERROR_R", "2");
            notesGroupFragment = new ECSingleNotesGroupFragment();
            fragmentTransaction.replace(R.id.frameLayoutFragmentExpenseCalculation, notesGroupFragment);
            fragmentTransaction.commit();
        }

        if (selectedNotesListIndex >= 0 && selectedNotesListIndex < notesGroupModelArrayList.size()) {
            Log.e("ERROR_R", "3: index: " + selectedNotesListIndex + ", size: " + notesGroupModelArrayList.size());
            ((ECSingleNotesGroupFragment)notesGroupFragment).updateData(this, notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList(), notesListModelArrayList.get(selectedNotesListIndex));
        }
    }



    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        selectedNotesListIndex = viewHolder.getAdapterPosition();
        setSingleNotesGroupFragment();
    }


    @Override
    public void onRemoveNotesGroupButtonClicked() {

        long id = notesListModelArrayList.get(selectedNotesListIndex).getGroupId();

        dataSource.open();
        dataSource.deleteNotesList(id);
        dataSource.deleteAllSingleNotesByGroupId(id);
        dataSource.close();

        Log.e("ERROR_RR", "" + id);

        if (selectedNotesListIndex >= 0 && selectedNotesListIndex < notesGroupModelArrayList.size()) {
            notesListModelArrayList.remove(selectedNotesListIndex);
            notesGroupModelArrayList.remove(selectedNotesListIndex);
        } else {
            selectedNotesListIndex = -1;
        }

        if (selectedNotesListIndex >= notesListModelArrayList.size()) {
            selectedNotesListIndex = notesListModelArrayList.size() - 1;
        }

        adapter.updateData(notesListModelArrayList, selectedNotesListIndex);
        setSingleNotesGroupFragment();
    }

    @Override
    public void onAddSingleNoteButtonClicked() {
        long groupId = notesListModelArrayList.get(selectedNotesListIndex).getGroupId();
        boolean isExpanse = true;
        int sum = 0;
        Date date = new Date();
        String currency = Constants.DRAM;
        String description = "";

        ECSingleNoteModel singleNoteModel = new ECSingleNoteModel();
        singleNoteModel.setGroupId(groupId);
        singleNoteModel.setExpense(isExpanse);
        singleNoteModel.setSum(sum);
        singleNoteModel.setDate(date);
        singleNoteModel.setCurrency(currency);
        singleNoteModel.setDescription(description);

        if (selectedNotesListIndex >= 0 && selectedNotesListIndex < notesGroupModelArrayList.size()) {
            notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().add(singleNoteModel);
        } else {
            Log.e("ERROR_R", "Out of Size >>> index: " + selectedNotesListIndex + ", size: " + notesGroupModelArrayList.size());
        }

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateAsString = format.format(date);

        dataSource.open();
        dataSource.addSingleNote(groupId, isExpanse, sum, dateAsString, currency, description);
        dataSource.close();

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment notesGroupFragment = fragmentManager.findFragmentById(R.id.frameLayoutFragmentExpenseCalculation);

        ((ECSingleNotesGroupFragment)notesGroupFragment).updateData(this, notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList(), notesListModelArrayList.get(selectedNotesListIndex));
    }

    @Override
    public void onDataChanged(int singleNotePosition, long id, long groupId, boolean isExpanse, double sum, String dateString, String currency, String description) {

        dataSource.open();
        dataSource.updateSingleNote(id, groupId, isExpanse, sum, dateString, currency, description);
        dataSource.close();

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment notesGroupFragment = fragmentManager.findFragmentById(R.id.frameLayoutFragmentExpenseCalculation);

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().get(singleNotePosition).setExpense(isExpanse);
        notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().get(singleNotePosition).setSum(sum);
        notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().get(singleNotePosition).setDate(date);
        notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().get(singleNotePosition).setCurrency(currency);
        notesGroupModelArrayList.get(selectedNotesListIndex).getSingleNoteModelsList().get(singleNotePosition).setDescription(description);

        ((ECSingleNotesGroupFragment)notesGroupFragment).onDataChanged(singleNotePosition, id, groupId, isExpanse, sum, dateString, currency, description);
    }

    @Override
    public void onSingleNoteRemoveButtonClicked(int singleNotePosition, long id) {
        dataSource.open();
        int removedId = dataSource.deleteSingleNote(id);
        dataSource.close();

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment notesGroupFragment = fragmentManager.findFragmentById(R.id.frameLayoutFragmentExpenseCalculation);

        ((ECSingleNotesGroupFragment)notesGroupFragment).removeSingleNote(singleNotePosition, id);
        Log.e("XX_XX_XX_XX", "onSingleNoteRemoveButtonClicked : " + removedId);
    }

    @Override
    public void onDateAndTimeSet(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment notesGroupFragment = fragmentManager.findFragmentById(R.id.frameLayoutFragmentExpenseCalculation);

        ((ECSingleNotesGroupFragment)notesGroupFragment).onDateSet(year, month, dayOfMonth, hourOfDay, minute);
    }
}

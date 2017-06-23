package com.example.sargiskh.expensecalculation.expensecalculation.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sargiskh.expensecalculation.MainActivity;
import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.dialogFragments.ExpenseCalculationEditDialogFragment;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.expensecalculation.viewHolders.ECNotesGroupViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECSingleNotesGroupListViewAdapter extends BaseAdapter {

    public interface OnECNotesGroupListViewAdapterListener {
        void onSingleNoteRemoveButtonClicked(int position, long id);
    }
    private OnECNotesGroupListViewAdapterListener notesGroupListViewAdapterListener;

    private ExpenseCalculationEditDialogFragment dialogFragment;

    private ArrayList<ECSingleNoteModel> singleNoteModelArrayList = new ArrayList<ECSingleNoteModel>();
    private Activity activity;
    private LayoutInflater inflater;

    private boolean isEditMode = false;

    public ECSingleNotesGroupListViewAdapter(Activity _activity, ArrayList<ECSingleNoteModel> _singleNoteModelArrayList) {

        activity = _activity;
        notesGroupListViewAdapterListener = ((MainActivity)activity).getECFragment();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        singleNoteModelArrayList = _singleNoteModelArrayList;
    }

    @Override
    public int getCount() {
        return singleNoteModelArrayList.size();
    }

    @Override
    public ECSingleNoteModel getItem(int position) {
        return singleNoteModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ECNotesGroupViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ec_notes_group_list_view_item, null, false);

            viewHolder = new ECNotesGroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ECNotesGroupViewHolder)convertView.getTag();
        }

        viewHolder.setData(singleNoteModelArrayList.get(position), isEditMode);

        viewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleNoteModelArrayList.get(position).isDetailViewExpended()) {
                    viewHolder.detailView.setVisibility(View.GONE);
                    singleNoteModelArrayList.get(position).setDetailViewExpended(false);
                } else {
                    viewHolder.detailView.setVisibility(View.VISIBLE);
                    singleNoteModelArrayList.get(position).setDetailViewExpended(true);
                }
            }
        });

        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager ft = ((MainActivity)activity).getSupportFragmentManager();
                dialogFragment = new ExpenseCalculationEditDialogFragment();

                Bundle bundle = new Bundle();

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = format.format(singleNoteModelArrayList.get(position).getDate());

                bundle.putInt("singleNotePosition", position);
                bundle.putLong("id", singleNoteModelArrayList.get(position).getId());
                bundle.putLong("groupId", singleNoteModelArrayList.get(position).getGroupId());
                bundle.putBoolean("isExpanse", singleNoteModelArrayList.get(position).isExpense());
                bundle.putDouble("sum", singleNoteModelArrayList.get(position).getSum());
                bundle.putString("date", dateString);
                bundle.putString("currency", singleNoteModelArrayList.get(position).getCurrency());
                bundle.putString("description", singleNoteModelArrayList.get(position).getDescription());

                dialogFragment.setArguments(bundle);

                dialogFragment.setOnDataChangeListener(((MainActivity)activity).getECFragment());

                dialogFragment.show(ft, "CurrencyDialogFragment");
            }
        });

        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesGroupListViewAdapterListener.onSingleNoteRemoveButtonClicked(position, singleNoteModelArrayList.get(position).getId());
            }
        });

        return convertView;
    }

    public void removeSingleNote(int singleNotePosition, long id) {
        singleNoteModelArrayList.remove(singleNoteModelArrayList.get(singleNotePosition));
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<ECSingleNoteModel> _singleNoteModelsArrayList) {
        singleNoteModelArrayList = _singleNoteModelsArrayList;
        notifyDataSetChanged();
    }

    public void updateEditMode(boolean _isEditMode) {
        isEditMode = _isEditMode;
        notifyDataSetChanged();
    }


    public void onDateSet(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        if (dialogFragment != null) {
            dialogFragment.onDateSet(year, month, dayOfMonth, hourOfDay, minute);
        }
    }

    public void OnDataChanged(int singleNotePosition, long id, long groupId, boolean isExpanse, double sum, String dateString, String currency, String description) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        singleNoteModelArrayList.get(singleNotePosition).setExpense(isExpanse);
        singleNoteModelArrayList.get(singleNotePosition).setSum(sum);
        singleNoteModelArrayList.get(singleNotePosition).setDate(date);
        singleNoteModelArrayList.get(singleNotePosition).setCurrency(currency);
        singleNoteModelArrayList.get(singleNotePosition).setDescription(description);
        notifyDataSetChanged();
    }

}

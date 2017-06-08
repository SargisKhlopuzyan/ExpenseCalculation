package com.example.sargiskh.expensecalculation.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;

import com.example.sargiskh.expensecalculation.MainActivity;
import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.dialogFragments.ExpenseCalculationEditDialogFragment;
import com.example.sargiskh.expensecalculation.models.ECSingleNoteModel;
import com.example.sargiskh.expensecalculation.viewHolders.ECNotesGroupViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECNotesGroupListViewAdapter extends BaseAdapter {

    private ExpenseCalculationEditDialogFragment dialogFragment;

    private ArrayList<ECSingleNoteModel> singleNoteModelArrayList = new ArrayList<ECSingleNoteModel>();
    private Activity activity;
    private LayoutInflater inflater;

    private int noteIndex = 0;
    private boolean isEditMode = false;

    public ECNotesGroupListViewAdapter(Activity activity, ArrayList<ECSingleNoteModel> _singleNoteModelArrayList, int _noteIndex) {
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        noteIndex = _noteIndex;
        if (noteIndex < 0) {
            return;
        }
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

        viewHolder.setData( (position == 0 ? null : singleNoteModelArrayList.get(position-1)), singleNoteModelArrayList.get(position), isEditMode);

        viewHolder.detailView.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.textViewSum.setOnClickListener(new View.OnClickListener() {
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
                //TODO
                FragmentManager ft = ((MainActivity)activity).getSupportFragmentManager();
                dialogFragment = new ExpenseCalculationEditDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("sum", "" + singleNoteModelArrayList.get(position).getSum());

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String strDate = format.format(singleNoteModelArrayList.get(position).getDate());
                bundle.putString("date", "" + strDate);

                bundle.putString("description", "" + singleNoteModelArrayList.get(position).getDescription());
                bundle.putInt("position", position);

                dialogFragment.setArguments(bundle);
                dialogFragment.show(ft, "ExpenseCalculationEditDialogFragment");
            }
        });

        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                singleNoteModelArrayList.remove(singleNoteModelArrayList.get(position));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


    public void updateData(ArrayList<ECSingleNoteModel> _singleNoteModelsArrayList, int _position) {
        singleNoteModelArrayList = _singleNoteModelsArrayList;
        noteIndex = _position;
        notifyDataSetChanged();
    }

    public void updateEditMode(boolean _isEditMode) {
        isEditMode = _isEditMode;
        notifyDataSetChanged();
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (dialogFragment != null) {
            dialogFragment.onDateSet(view, year, month, dayOfMonth);
        }
    }

    public void OnDataChanged(int position, String sum, String dateString, String description) {
        singleNoteModelArrayList.get(position).setSum(Float.valueOf(sum));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        singleNoteModelArrayList.get(position).setDate(date);
        singleNoteModelArrayList.get(position).setDescription(description);
        notifyDataSetChanged();
    }

}

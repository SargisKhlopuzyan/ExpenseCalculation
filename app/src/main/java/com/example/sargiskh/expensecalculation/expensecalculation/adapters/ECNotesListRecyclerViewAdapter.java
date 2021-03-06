package com.example.sargiskh.expensecalculation.expensecalculation.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.ItemTouchHelperAdapter;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.ItemTouchHelperViewHolder;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.OnItemClickListener;
import com.example.sargiskh.expensecalculation.expensecalculation.helper.OnStartDragListener;
import com.example.sargiskh.expensecalculation.expensecalculation.models.ECNotesListModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sargiskh on 6/1/2017.
 */

public class ECNotesListRecyclerViewAdapter extends RecyclerView.Adapter<ECNotesListRecyclerViewAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private int notesListSelectedIndex;

    // parent activity will implement this method to respond to click events
    private final OnItemClickListener mItemClickListener;
    private final OnStartDragListener mDragStartListener;

    private ArrayList<ECNotesListModel> notesListModelArrayList = new ArrayList<ECNotesListModel>();
    private LayoutInflater inflater;

    // data is passed into the constructor
    public ECNotesListRecyclerViewAdapter(Activity _activity, LinearLayoutManager _linearLayoutManager, OnItemClickListener _itemClickListener, OnStartDragListener _dragStartListener, ArrayList<ECNotesListModel> _notesListModelArrayList) {
        activity = _activity;
        linearLayoutManager = _linearLayoutManager;
        mItemClickListener = _itemClickListener;
        mDragStartListener = _dragStartListener;
        notesListModelArrayList = _notesListModelArrayList;

        if (notesListModelArrayList.size() > 0) {
            notesListSelectedIndex = 0;
        }
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(ArrayList<ECNotesListModel> _notesListModelArrayList, int _noteSelectedPosition) {
        notesListModelArrayList = _notesListModelArrayList;
        notesListSelectedIndex = _noteSelectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ec_recyclerview_item_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        holder.initData(notesListModelArrayList.get(position), notesListSelectedIndex == position);

        holder.containerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDrag(holder);
                return false;
            }
        });

        holder.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notesListSelectedIndex == position ) {
                    return;
                }
                int exNotesListSelectedIndex = notesListSelectedIndex;
                notesListSelectedIndex = position;
                notifyItemChanged(exNotesListSelectedIndex);
                notifyItemChanged(notesListSelectedIndex);

                notifyItemChanged(notesListSelectedIndex);
                notesListSelectedIndex = position;
                notifyItemChanged(position);
                mItemClickListener.onItemClick(holder);
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        Log.e("IIIIII", "onItemDismiss : " + position);
        notesListModelArrayList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.e("IIIIII", "onItemMove : from : " + fromPosition + " toPosition " + toPosition);
        Collections.swap(notesListModelArrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return notesListModelArrayList.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public final View containerView;
        public final TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            containerView = itemView.findViewById(R.id.item);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

        public void initData(ECNotesListModel model, boolean _isSelected) {
            textView.setText(model.getGroupName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (_isSelected) {
                    textView.setBackground(ContextCompat.getDrawable(containerView.getContext(), R.color.ec_button_selected_blue_transparent));
                } else {
                    textView.setBackground(ContextCompat.getDrawable(containerView.getContext(), R.color.ec_blue_fb));
                }
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
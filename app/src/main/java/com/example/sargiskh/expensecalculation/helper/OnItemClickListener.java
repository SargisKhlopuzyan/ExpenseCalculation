package com.example.sargiskh.expensecalculation.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by sargiskh on 6/1/2017.
 */
/**
 * Listener for manual initiation of a item click.
 */
public interface OnItemClickListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onItemClick(RecyclerView.ViewHolder viewHolder);

}

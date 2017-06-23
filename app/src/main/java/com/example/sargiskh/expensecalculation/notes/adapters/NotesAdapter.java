package com.example.sargiskh.expensecalculation.notes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sargiskh.expensecalculation.R;

import java.util.ArrayList;

/**
 * Created by sargiskh on 6/13/2017.
 */

public class NotesAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> notes;

    public NotesAdapter(Activity _activity, ArrayList<String> _notes) {
        activity = _activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notes = _notes;
    }


    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ECNotesGroupViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.notes_list_view_item_layout, null, false);

            viewHolder = new ECNotesGroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ECNotesGroupViewHolder)convertView.getTag();
        }

        viewHolder.setData(notes.get(position));

        return convertView;
    }


    public class ECNotesGroupViewHolder {

        public TextView textViewNote;

        public ECNotesGroupViewHolder(View convertView) {
            textViewNote = (TextView)convertView.findViewById(R.id.textViewNote);
        }

        public void setData(final String str) {
            textViewNote.setText(str);
        }
    }
}

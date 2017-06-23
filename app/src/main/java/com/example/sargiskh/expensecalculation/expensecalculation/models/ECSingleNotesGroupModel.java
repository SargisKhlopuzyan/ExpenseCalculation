package com.example.sargiskh.expensecalculation.expensecalculation.models;

import java.util.ArrayList;

/**
 * Created by sargiskh on 6/6/2017.
 */

public class ECSingleNotesGroupModel {

    private String groupName = "";

    private ArrayList<ECSingleNoteModel> singleNoteModelsList = new ArrayList<>();


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String _groupName) {
        groupName = _groupName;
    }


    public ArrayList<ECSingleNoteModel> getSingleNoteModelsList() {
        return singleNoteModelsList;
    }

    public void setSingleNoteModelsList(ArrayList<ECSingleNoteModel> _singleNoteModelList) {
        singleNoteModelsList.clear();
        for (int i = 0; i < _singleNoteModelList.size(); i++) {
            singleNoteModelsList.add(_singleNoteModelList.get(i));
        }
    }
}

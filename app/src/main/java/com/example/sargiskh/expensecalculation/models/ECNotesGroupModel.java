package com.example.sargiskh.expensecalculation.models;

import java.util.ArrayList;

/**
 * Created by sargiskh on 6/6/2017.
 */

public class ECNotesGroupModel {

    private ArrayList<ECSingleNoteModel> singleNoteModelsList = new ArrayList<>();

    private ECNotesListModel notesListModel = new ECNotesListModel();

    public ArrayList<ECSingleNoteModel> getSingleNoteModelsList() {
        return singleNoteModelsList;
    }

    public void setSingleNoteModelsList(ArrayList<ECSingleNoteModel> _singleNoteModelList) {
        this.singleNoteModelsList.clear();
        for (int i = 0; i < _singleNoteModelList.size(); i++) {
            this.singleNoteModelsList.add(_singleNoteModelList.get(i));
        }
    }

    public ECNotesListModel getNotesListModel() {
        return notesListModel;
    }

    public void setNotesListModel(ECNotesListModel _notesListModel) {
        notesListModel = _notesListModel;
    }
}

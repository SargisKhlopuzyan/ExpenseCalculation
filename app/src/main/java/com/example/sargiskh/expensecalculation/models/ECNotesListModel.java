package com.example.sargiskh.expensecalculation.models;

import java.util.Date;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECNotesListModel {

    private boolean isItemSelected = false;
    private int itemPosition = 0;

    private String itemDescription;

    public ECNotesListModel() {

    }

    public ECNotesListModel(String itemDescription, int itemPosition, boolean isItemSelected) {
        this.itemDescription = itemDescription;
        this.itemPosition = itemPosition;
        this.isItemSelected = isItemSelected;
    }

    public boolean getItemSelectedState() {
        return isItemSelected;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public String getItemDescription() {
        return itemDescription;
    }


    public void setItemSelectedState(boolean isItemSelected) {
        this.isItemSelected = isItemSelected;
    }

    public void setItemPosition(int _itemPosition) {
        itemPosition = _itemPosition;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}

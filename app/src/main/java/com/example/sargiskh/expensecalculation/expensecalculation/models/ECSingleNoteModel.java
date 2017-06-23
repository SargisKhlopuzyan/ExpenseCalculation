package com.example.sargiskh.expensecalculation.expensecalculation.models;

import com.example.sargiskh.expensecalculation.expensecalculation.utils.Constants;

import java.util.Date;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECSingleNoteModel {

    private boolean isDetailViewExpended = false;

    private boolean isExpense = true;
    private long id = 0;
    private long groupId = 0;
    private double sum = 0;
    private Date date = new Date();
    private String currency = Constants.DRAM;
    private String description = "";


    public ECSingleNoteModel() {

    }

    public boolean isDetailViewExpended() {
        return isDetailViewExpended;
    }

    public void setDetailViewExpended(boolean detailViewExpended) {
        isDetailViewExpended = detailViewExpended;
    }

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long _groupId) {
        groupId = _groupId;
    }


    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean _expense) {
        isExpense = _expense;
    }


    public double getSum() {
        return sum;
    }

    public void setSum(double _sum) {
        sum = _sum;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date _date) {
        date = _date;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String _currency) {
        currency = _currency;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        description = _description;
    }

}

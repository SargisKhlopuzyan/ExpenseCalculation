package com.example.sargiskh.expensecalculation.models;

import com.example.sargiskh.expensecalculation.utils.Constants;

import java.util.Date;

/**
 * Created by sargiskh on 5/29/2017.
 */

public class ECSingleNoteModel {

    private boolean isDetailViewExpended = false;
    private String currency = Constants.DRAM;

    private float sum;
    private boolean isExpense = true;
    private Date date;
    private String description;

    public ECSingleNoteModel() {

    }

    public boolean isDetailViewExpended() {
        return isDetailViewExpended;
    }

    public String getCurrency() {
        return currency;
    }

    public float getSum() {
        return sum;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public void setDetailViewExpended(boolean detailViewExpended) {
        isDetailViewExpended = detailViewExpended;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

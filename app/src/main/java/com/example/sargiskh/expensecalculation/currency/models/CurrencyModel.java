package com.example.sargiskh.expensecalculation.currency.models;

/**
 * Created by sargiskh on 6/21/2017.
 */

public class CurrencyModel {

    private long id = 0;
    private boolean isMainCurrency = false;
    private boolean isVisible = false;
    private String fullName = "";
    private String shortName = "";
    private String simbole = "";
    private double currencyRate = 0;

    public CurrencyModel(long id, boolean isMainCurrency, boolean isVisible, String fullName, String shortName, String simbole, double currencyRate) {
        this.id = id;
        this.isMainCurrency = isMainCurrency;
        this.isVisible = isVisible;
        this.fullName = fullName;
        this.shortName = shortName;
        this.simbole = simbole;
        this.currencyRate = currencyRate;
    }

    public long getId() {
        return id;
    }

    public boolean isMainCurrency() {
        return isMainCurrency;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSimbole() {
        return simbole;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setMainCurrency(boolean mainCurrency) {
        isMainCurrency = mainCurrency;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setSimbole(String simbole) {
        this.simbole = simbole;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }
}

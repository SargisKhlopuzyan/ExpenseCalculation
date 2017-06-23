package com.example.sargiskh.expensecalculation.currency.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by sargiskh on 6/20/2017.
 */

public class CurrencyDataSource {

    private SQLiteDatabase database;
    private CurrencySQLiteOpenHelper dbHelper;

    private String[] allColumnsCurrency = {CurrencySQLiteOpenHelper.ID, CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY, CurrencySQLiteOpenHelper.IS_VISIBLE, CurrencySQLiteOpenHelper.FULL_NAME, CurrencySQLiteOpenHelper.SHORT_NAME, CurrencySQLiteOpenHelper.SIMBOLE, CurrencySQLiteOpenHelper.CURRENCY_RATE};

    public CurrencyDataSource(Context context) {
        dbHelper = new CurrencySQLiteOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }


    public long addCurrency(boolean _isMainCurrency, boolean _isVisible, String fullName, String shortName, String simbole, double currencyRate) {

        int isMainCurrency = _isMainCurrency == true ? 0 : 1;
        int isVisible = _isVisible == true ? 0 : 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY, isMainCurrency);
        contentValues.put(CurrencySQLiteOpenHelper.IS_VISIBLE, isVisible);

        contentValues.put(CurrencySQLiteOpenHelper.FULL_NAME, fullName);
        contentValues.put(CurrencySQLiteOpenHelper.SHORT_NAME, shortName);
        contentValues.put(CurrencySQLiteOpenHelper.SIMBOLE, simbole);

        contentValues.put(CurrencySQLiteOpenHelper.CURRENCY_RATE, currencyRate);

        long id = database.insert(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateCurrency(long id, String fullName, String shortName, String simbole) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencySQLiteOpenHelper.FULL_NAME, fullName);
        contentValues.put(CurrencySQLiteOpenHelper.SHORT_NAME, shortName);
        contentValues.put(CurrencySQLiteOpenHelper.SIMBOLE, simbole);
        int returnedId = database.update(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, contentValues, "_id=" + id, null);
        return returnedId;
    }

    public int deleteCurrency(long id) {
        int removedCount = database.delete(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, CurrencySQLiteOpenHelper.ID + " = " + id, null);
        return removedCount;
    }

    public Cursor getAllCurrency() {
        Cursor cursor = database.query(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, allColumnsCurrency, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void setMainCurrency(long mainCurrencyId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY, 1);
        String[] whereArgs = {"0"};
        database.update(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, contentValues, CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY + "=?", whereArgs);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY, 0);
        String[] whereArgs1 = new String[] {Long.toString(mainCurrencyId)};
        int sss = database.update(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, contentValues1, CurrencySQLiteOpenHelper.ID + "=?", whereArgs1);
        Log.e("XXXXXXXXXXXXXXXXX", "sss: " + sss);
    }
}

package com.example.sargiskh.expensecalculation.currency.helpers;

import android.database.Cursor;

import com.example.sargiskh.expensecalculation.currency.database.CurrencySQLiteOpenHelper;
import com.example.sargiskh.expensecalculation.currency.models.CurrencyModel;

import java.util.ArrayList;

/**
 * Created by sargiskh on 6/21/2017.
 */

public class Converters {

    public static void convertCursorToCurrencyModels(Cursor cursor, ArrayList<CurrencyModel> currencyModelArrayList) {

        currencyModelArrayList.clear();

        long id = 0;
        boolean isMainCurrency = false;
        boolean isVisible = false;
        String fullName = "";
        String shortName = "";
        String simbole = "";
        double currencyRate = 0;

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getLong(cursor.getColumnIndex(CurrencySQLiteOpenHelper.ID));
                isMainCurrency = cursor.getInt(cursor.getColumnIndex(CurrencySQLiteOpenHelper.IS_MAIN_CURRENCY)) == 0 ? true : false;
                isVisible = cursor.getInt(cursor.getColumnIndex(CurrencySQLiteOpenHelper.IS_VISIBLE)) == 0 ? true : false;
                fullName = cursor.getString(cursor.getColumnIndex(CurrencySQLiteOpenHelper.FULL_NAME));
                shortName = cursor.getString(cursor.getColumnIndex(CurrencySQLiteOpenHelper.SHORT_NAME));
                simbole = cursor.getString(cursor.getColumnIndex(CurrencySQLiteOpenHelper.SIMBOLE));
                currencyRate = cursor.getDouble(cursor.getColumnIndex(CurrencySQLiteOpenHelper.CURRENCY_RATE));

                CurrencyModel currencyModel = new CurrencyModel(id, isMainCurrency, isVisible, fullName, shortName, simbole, currencyRate);
                currencyModelArrayList.add(currencyModel);
            } while (cursor.moveToNext());
        }
    }

}

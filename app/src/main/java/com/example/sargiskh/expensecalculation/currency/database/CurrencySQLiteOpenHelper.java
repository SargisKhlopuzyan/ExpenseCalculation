package com.example.sargiskh.expensecalculation.currency.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sargiskh on 6/12/2017.
 */

public class CurrencySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Currency.db";
    public static final int DATABASE_VERSION = 1;
    //
    public static final String CURRENCY_TABLE_NAME = "currency";
    //
    public static final String ID = "_id";
    public static final String IS_MAIN_CURRENCY = "isMainCurrency";
    public static final String IS_VISIBLE = "isVisible";
    public static final String FULL_NAME = "fullName";
    public static final String SHORT_NAME = "shortName";
    public static final String SIMBOLE = "simbole";
    public static final String CURRENCY_RATE = "currencyRate";

    public CurrencySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_SINGLE_NOTE_TABLE = "CREATE TABLE " + CURRENCY_TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + IS_MAIN_CURRENCY + " INTEGER NOT NULL,"
                + IS_VISIBLE + " INTEGER NOT NULL,"
                + FULL_NAME + " TEXT NOT NULL,"
                + SHORT_NAME + " TEXT NOT NULL,"
                + SIMBOLE + " TEXT NOT NULL,"
                + CURRENCY_RATE + " INTEGER NOT NULL);";

        db.execSQL(CREATE_SINGLE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + CURRENCY_TABLE_NAME);
        onCreate(db);
    }
}

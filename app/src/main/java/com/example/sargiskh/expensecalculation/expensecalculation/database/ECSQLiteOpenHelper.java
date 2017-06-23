package com.example.sargiskh.expensecalculation.expensecalculation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sargiskh on 6/12/2017.
 */

public class ECSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String EXPENSE_CALCULATION_DATABASE_NAME = "ExpenseCalculation.db";
    public static final int EXPENSE_CALCULATION_DATABASE_VERSION = 1;
    //
    public static final String SINGLE_NOTE_TABLE_NAME = "singleNote";
    public static final String NOTES_LIST_TABLE_NAME = "notesList";
    //
    public static final String ID = "_id";
    public static final String GROUP_ID = "groupId";
    public static final String PRIORITY = "priority";

    public static final String GROUP_NAME = "groupName";
    public static final String IS_EXPENSE = "isExpense";
    public static final String SUM = "sum";
    public static final String DATE_AS_STRING = "date_as_string";
    public static final String CURRENCY = "currency";
    public static final String DESCRIPTION = "description";

    public ECSQLiteOpenHelper(Context context) {
        super(context, EXPENSE_CALCULATION_DATABASE_NAME, null, EXPENSE_CALCULATION_DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_NOTES_LIST_TABLE = "CREATE TABLE " + NOTES_LIST_TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY,"
                + GROUP_NAME + " TEXT NOT NULL,"
                + PRIORITY + " INTEGER NOT NULL);";

        final String CREATE_SINGLE_NOTE_TABLE = "CREATE TABLE " + SINGLE_NOTE_TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + GROUP_ID + " TEXT NOT NULL,"
                + IS_EXPENSE + " INTEGER NOT NULL,"
                + SUM + " REAL NOT NULL,"
                + DATE_AS_STRING + " TEXT NOT NULL,"
                + CURRENCY + " TEXT NOT NULL,"
                + DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(CREATE_NOTES_LIST_TABLE);
        db.execSQL(CREATE_SINGLE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + NOTES_LIST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + SINGLE_NOTE_TABLE_NAME);
        onCreate(db);
    }
}

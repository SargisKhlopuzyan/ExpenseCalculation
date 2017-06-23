package com.example.sargiskh.expensecalculation.expensecalculation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sargiskh on 6/12/2017.
 */

public class ECDataSource {
    private SQLiteDatabase database;
    private ECSQLiteOpenHelper dbHelper;

    private String[] allColumnsNotesList = {ECSQLiteOpenHelper.ID, ECSQLiteOpenHelper.GROUP_NAME, ECSQLiteOpenHelper.PRIORITY};
    private String[] allColumnsSingleNote = {ECSQLiteOpenHelper.ID, ECSQLiteOpenHelper.GROUP_ID, ECSQLiteOpenHelper.IS_EXPENSE, ECSQLiteOpenHelper.SUM, ECSQLiteOpenHelper.DATE_AS_STRING, ECSQLiteOpenHelper.CURRENCY, ECSQLiteOpenHelper.DESCRIPTION};

    public ECDataSource(Context context) {
        dbHelper = new ECSQLiteOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }



    // isExpanse: 0 (false) and 1 (true)
    public long addSingleNote(long groupId, boolean isExpanse, double sum, String date, String currency, String description) {

        int expanseSign = isExpanse ? 1 : 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(ECSQLiteOpenHelper.GROUP_ID, groupId);
        contentValues.put(ECSQLiteOpenHelper.IS_EXPENSE, expanseSign);
        contentValues.put(ECSQLiteOpenHelper.SUM, sum);
        contentValues.put(ECSQLiteOpenHelper.DATE_AS_STRING, date);
        contentValues.put(ECSQLiteOpenHelper.CURRENCY, currency);
        contentValues.put(ECSQLiteOpenHelper.DESCRIPTION, description);

        long id = database.insert(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateSingleNote(long id, long groupId, boolean isExpanse, double sum, String dateString, String currency, String description) {

        int expanseSign = isExpanse ? 1 : 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(ECSQLiteOpenHelper.GROUP_ID, groupId);
        contentValues.put(ECSQLiteOpenHelper.IS_EXPENSE, expanseSign);
        contentValues.put(ECSQLiteOpenHelper.SUM, sum);
        contentValues.put(ECSQLiteOpenHelper.DATE_AS_STRING, dateString);
        contentValues.put(ECSQLiteOpenHelper.CURRENCY, currency);
        contentValues.put(ECSQLiteOpenHelper.DESCRIPTION, description);

        int returnedId = database.update(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, contentValues, "_id=" + id, null);

//        long returnedId = database.insert(CurrencySQLiteOpenHelper.CURRENCY_TABLE_NAME, null, contentValues);
        return returnedId;
    }

    public int deleteSingleNote(long id) {
        int removedCount = database.delete(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, ECSQLiteOpenHelper.ID + " = " + id, null);
        return removedCount;
    }

    public Cursor getAllSingleNotes() {
        Cursor cursor = database.query(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, allColumnsSingleNote, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public int deleteAllSingleNotesByGroupId(long _groupId) {

        String[] whereArgs = {"" + _groupId};
        int removedCount = database.delete(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, ECSQLiteOpenHelper.GROUP_ID + " = " + _groupId, null);
        return removedCount;
    }

    public Cursor getAllSingleNotesBySelection(String selection, String[] selectionArg) {
        Cursor cursor = database.query(ECSQLiteOpenHelper.SINGLE_NOTE_TABLE_NAME, allColumnsSingleNote, selection, selectionArg, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    //

    public long addNotesList(String groupName, int priority) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ECSQLiteOpenHelper.GROUP_NAME, groupName);
        contentValues.put(ECSQLiteOpenHelper.PRIORITY, priority);

        long id = database.insert(ECSQLiteOpenHelper.NOTES_LIST_TABLE_NAME, null, contentValues);
        return id;
    }

    public int deleteNotesList(long id) {
        int removedCount = database.delete(ECSQLiteOpenHelper.NOTES_LIST_TABLE_NAME, ECSQLiteOpenHelper.ID + " = " + id, null);
        return removedCount;
    }

    public Cursor getAllNotesLists() {
        Cursor cursor = database.query(ECSQLiteOpenHelper.NOTES_LIST_TABLE_NAME, allColumnsNotesList, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}

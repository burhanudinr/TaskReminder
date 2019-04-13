package com.burhanudin.taskreminder.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.burhanudin.taskreminder.model.Reminder;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAMA = "taskreminder_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAMA, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Reminder.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE);
        onCreate(db);
    }

    public boolean insertReminder(Reminder reminder) {
        boolean isSuccess = false;

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(Reminder.KOLOM_NAMA, reminder.getNama());
        values.put(Reminder.KOLOM_DSC, reminder.getDsc());
        values.put(Reminder.KOLOM_TIMESTAMP, reminder.getTimestamp());

        isSuccess = db.insert(Reminder.TABLE, null, values) > 0;

        db.close();
        return isSuccess;
    }

    public Reminder getReminder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Reminder.TABLE,
                new String[]{Reminder.KOLOM_ID, Reminder.KOLOM_NAMA, Reminder.KOLOM_DSC,
                        Reminder.KOLOM_PATH_IMG, Reminder.KOLOM_TIMESTAMP},
                Reminder.KOLOM_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Reminder reminder = new Reminder(
                cursor.getInt(cursor.getColumnIndex(Reminder.KOLOM_ID)),
                cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_NAMA)),
                cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_DSC)),
                cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_PATH_IMG)),
                cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_TIMESTAMP)));

        return reminder;
    }

    public ArrayList<Reminder> getAllReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Reminder.TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(Reminder.KOLOM_ID)));
                reminder.setNama(cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_NAMA)));
                reminder.setDsc(cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_DSC)));
                reminder.setPathimg(cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_PATH_IMG)));
                reminder.setTimestamp(cursor.getString(cursor.getColumnIndex(Reminder.KOLOM_TIMESTAMP)));
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        db.close();
        return reminders;
    }

    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Reminder.TABLE, Reminder.KOLOM_ID + " = ?",
                new String[]{String.valueOf(reminder.getId())});
        db.close();
    }
}

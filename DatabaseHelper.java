package com.example.medi_ai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // DATABASE INFO
    private static final String DATABASE_NAME = "MediAI.db";
    private static final int DATABASE_VERSION = 1;

    // TABLE
    public static final String TABLE_PATIENT = "patients";

    // COLUMN NAMES
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_AGE = "age";
    public static final String COL_DISEASE = "disease";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // CREATE TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PATIENT + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_AGE + " INTEGER NOT NULL, "
                + COL_DISEASE + " TEXT NOT NULL"
                + ")";

        db.execSQL(query);
    }

    // UPGRADE DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        onCreate(db);
    }

    // 🟢 INSERT PATIENT
    public boolean insertPatient(String name, int age, String disease) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_DISEASE, disease);

        long result = db.insert(TABLE_PATIENT, null, values);
        db.close();

        return result != -1;
    }

    // 🟢 GET ALL PATIENTS
    public Cursor getAllPatients() {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PATIENT, null);
    }

    // 🟢 UPDATE PATIENT
    public boolean updatePatient(int id, String name, int age, String disease) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_AGE, age);
        values.put(COL_DISEASE, disease);

        int result = db.update(TABLE_PATIENT, values, COL_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
        return result > 0;
    }

    // 🟢 DELETE PATIENT
    public boolean deletePatient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_PATIENT, COL_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
        return result > 0;
    }
}

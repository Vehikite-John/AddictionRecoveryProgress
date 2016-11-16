package com.example.addictionrecoveryprogress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handle database creation and upgrade events
 */
class ProgressDBHandler extends SQLiteOpenHelper {
    // SQLite database for progress records and statistics
    private static final String DB_NAME = "progress.db";
    private static final int DB_VERSION = 1;

    // Progress records table name and columns
    static final String TABLE_RECORDS = "records";
    static final String COL_REC_ID = "id";             // INTEGER autoincrement
    static final String COL_REC_DATE = "date";         // INTEGER date.timeInMillis()
    static final String COL_REC_VICTORY = "victory";   // INTEGER (0 or 1)
    static final String COL_REC_MOOD = "mood";         // INTEGER mood.ordinal()
    static final String COL_REC_LOCATION = "location"; // INTEGER location.ordinal()
    static final String COL_REC_TIME = "time";         // INTEGER time.ordinal()

    // Progress records table creation statement
    private static final String TABLE_RECORDS_CREATE =
            "CREATE TABLE " + TABLE_RECORDS + " (" +
                    COL_REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_REC_DATE + " INTEGER NOT NULL UNIQUE, " +
                    COL_REC_VICTORY + " INTEGER, " +
                    COL_REC_MOOD + " INTEGER, " +
                    COL_REC_LOCATION + " INTEGER, " +
                    COL_REC_TIME + " INTEGER" +
                    ")";

    // Statistics table name and columns
    // TBD

    // Statistics table creation statement
    // TBD

    /**
     * Constructs new instance based on given Context
     *
     * @param context the Android context
     */
    ProgressDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_RECORDS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This will need to change if we ever advance the database version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        db.execSQL(TABLE_RECORDS_CREATE);
    }
}

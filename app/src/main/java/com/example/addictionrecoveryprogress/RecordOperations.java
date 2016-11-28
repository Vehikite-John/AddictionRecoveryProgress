package com.example.addictionrecoveryprogress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A persistent repository of ProgressRecords
 **/
class RecordOperations {
  private static final String REC_COLUMNS[] = {
      ProgressDBHandler.COL_REC_ID,
      ProgressDBHandler.COL_REC_DATE,
      ProgressDBHandler.COL_REC_VICTORY,
      ProgressDBHandler.COL_REC_MOOD,
      ProgressDBHandler.COL_REC_LOCATION,
      ProgressDBHandler.COL_REC_TIME
  };

    private static final String SQL_RUN_GROUP =
        "SELECT " + ProgressDBHandler.COL_REC_DATE + ", " +
            ProgressDBHandler.COL_REC_VICTORY + ", " +
            "(SELECT COUNT(*)" +
            " FROM " + ProgressDBHandler.TABLE_RECORDS + " R" +
            " WHERE R." + ProgressDBHandler.COL_REC_VICTORY +
            "    <> RR." + ProgressDBHandler.COL_REC_VICTORY +
            "  AND R." + ProgressDBHandler.COL_REC_DATE +
            "    <= RR." + ProgressDBHandler.COL_REC_DATE +
            ") AS runGroup " +
            "FROM " + ProgressDBHandler.TABLE_RECORDS + " RR";

    private static final String SQL_STREAK =
        "SELECT " + ProgressDBHandler.COL_REC_VICTORY + ", " +
            "  MIN(" + ProgressDBHandler.COL_REC_DATE + ") AS startDate, " +
            "  MAX(" + ProgressDBHandler.COL_REC_DATE + ") AS endDate, " +
            "  COUNT (*) AS length " +
            "FROM (" + SQL_RUN_GROUP + ") " +
            "GROUP BY " + ProgressDBHandler.COL_REC_VICTORY + ", runGroup " +
            "ORDER BY MIN(" + ProgressDBHandler.COL_REC_DATE + ")";

    private static final String SQL_MAX_STREAK =
        "SELECT * " +
            "FROM (" + SQL_STREAK + ") " +
            "WHERE " + ProgressDBHandler.COL_REC_VICTORY + " = 1 " +
            "ORDER BY length DESC " +
            "LIMIT 1";

    private static final String SQL_CUR_STREAK =
        "SELECT * " +
            "FROM (" + SQL_STREAK + ") " +
            "WHERE " + ProgressDBHandler.COL_REC_VICTORY + " = 1 " +
            "ORDER BY endDate DESC " +
            "LIMIT 1";

    private static final String SQL_TOTAL_VICTORIES =
        "SELECT COUNT(*) AS total " +
            "FROM " + ProgressDBHandler.TABLE_RECORDS + " " +
            "WHERE " + ProgressDBHandler.COL_REC_VICTORY + " = 1";

    private static final String SQL_RANGE_VICTORIES =
        "SELECT COUNT(*) AS total " +
            "FROM " + ProgressDBHandler.TABLE_RECORDS + " " +
            "WHERE " + ProgressDBHandler.COL_REC_VICTORY + " = 1" +
            " AND " + ProgressDBHandler.COL_REC_DATE + " >= ?" +
            " AND " + ProgressDBHandler.COL_REC_DATE + " <= ?";

    private static final String SQL_TOTAL_COUNT =
        "SELECT COUNT(*) AS totalCount " +
            "FROM " + ProgressDBHandler.TABLE_RECORDS;

    private ProgressDBHandler _dbHandler;
    private SQLiteDatabase _db;

    /**
     * Constructs an instance of RecordOperations using the given Context
     *
     * @param context the application context
     */
    RecordOperations(Context context) {
        _dbHandler = new ProgressDBHandler(context);
    }

    /**
     * Opens the ProgressRecord repository
     */
    void open() {
        _db = _dbHandler.getWritableDatabase();
    }

    /**
     * Closes the ProgressRecord repository
     */
    void close() {
        _dbHandler.close();
    }

    /**
     * Adds a ProgressRecord to the repository.
     *
     * @param record the ProgressRecord to be added
     * @return a copy of the ProgressRecord with the record ID set to a unique value.
     */
    ProgressRecord addRecord(ProgressRecord record) {

        // insert record into table
        long id = _db.insert(ProgressDBHandler.TABLE_RECORDS, null, mapRecordToContentValues(record));

        // update record with unique row ID
        record.setId(id);
        Logger.getLogger(ProgressRecord.class.getName()).log(Level.INFO, "ID:" + id + " added.");
        return record;
    }

  /**
   * Retrieve a ProgressRecord using the unique record ID.
   *
   * @param id the unique ProgressRecord ID.
   * @return the ProgressRecord
   */
  ProgressRecord getRecord(long id) {
    ProgressRecord record = null;
    Cursor cursor = _db.query(ProgressDBHandler.TABLE_RECORDS, REC_COLUMNS,
        ProgressDBHandler.COL_REC_ID + "=?", new String[]{String.valueOf(id)},
        null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
      record = mapCursorToRecord(cursor);
    }
    return record;
  }

  /**
   * Retrieve a ProgressRecord using the record's date.
   *
   * @param date the date for which progress was recorded.
   * @return the ProgressRecord
   */
  ProgressRecord getRecord(Calendar date) {
    ProgressRecord record = null;
    long millis = date.getTimeInMillis();
    Cursor cursor = _db.query(ProgressDBHandler.TABLE_RECORDS, REC_COLUMNS,
        ProgressDBHandler.COL_REC_DATE + "=?", new String[]{String.valueOf(millis)},
        null, null, null);
    // if record doesn't exist
    if (cursor.getCount() <= 0) {
      record = new ProgressRecord();
    }
    else {
      cursor.moveToFirst();
      record = mapCursorToRecord(cursor);
    }
    return record;
  }

  /**
   * Retrieves a list of all ProgressRecords in the repository.
   *
   * @return the List of ProgressRecords
   */
  List<ProgressRecord> getAllRecords() {
    List<ProgressRecord> records = new ArrayList<>();
    Cursor cursor = _db.query(ProgressDBHandler.TABLE_RECORDS, REC_COLUMNS,
        null, null, null, null, null);
    if (cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        records.add(mapCursorToRecord(cursor));
      }
    }
    return records;
  }

  /**
   * Updates an existing ProgressRecord in the repository
   *
   * @param record the record to update
   * @return the number of records updated
   */
  int updateRecord(ProgressRecord record) {
    return _db.update(ProgressDBHandler.TABLE_RECORDS, mapRecordToContentValues(record),
        ProgressDBHandler.COL_REC_ID + "=?", new String[]{String.valueOf(record.getId())});
  }

    int getLongestStreak() {
        int length = 0;
        Cursor cursor = _db.rawQuery(SQL_MAX_STREAK, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                length = cursor.getInt(cursor.getColumnIndex("length"));
            }
            cursor.close();
        }
        return length;
    }

    int getCurrentStreak() {
        int length = 0;
        Cursor cursor = _db.rawQuery(SQL_CUR_STREAK, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                length = cursor.getInt(cursor.getColumnIndex("length"));
            }
            cursor.close();
        }
        return length;
    }

    int getTotalVictories() {
        Cursor cursor = _db.rawQuery(SQL_TOTAL_VICTORIES, null);
        int total = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            }
            cursor.close();
        }
        return total;
    }

    int getMonthVictories() {
        // get the 1st day of the current month
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long start = date.getTimeInMillis();

        // get the last day of the current month
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        long end = date.getTimeInMillis();

        // get the # of victories in the date range
        int total = 0;
        Cursor cursor = _db.rawQuery(SQL_RANGE_VICTORIES, new String[]{String.valueOf(start),
            String.valueOf(end)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            }
            cursor.close();
        }
        return total;
    }

    int getTotalVictoriesPercent() {
        Cursor cursor = _db.rawQuery(SQL_TOTAL_COUNT, null);
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(cursor.getColumnIndex("totalCount"));
            }
            cursor.close();
        }

        // short-circuit return for empty table
        if (count == 0) {
            return 0;
        }

        return (getTotalVictories() * 100) / count;
    }

    int getMonthVictoriesPercent() {
        Calendar date = Calendar.getInstance();
        int count = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        return (getMonthVictories() * 100) / count;
    }

    private static ContentValues mapRecordToContentValues(ProgressRecord record) {
        // convert record values to DB data types
        ContentValues values = new ContentValues();
        Calendar date = record.getDate();
        values.put(ProgressDBHandler.COL_REC_DATE, date == null ? null : date.getTimeInMillis());
        values.put(ProgressDBHandler.COL_REC_VICTORY, record.isVictory() ? 1 : 0);
        ProgressRecord.Mood mood = record.getMood();
        values.put(ProgressDBHandler.COL_REC_MOOD, mood == null ? null : mood.ordinal());
        ProgressRecord.Location location = record.getLocation();
        values.put(ProgressDBHandler.COL_REC_LOCATION, location == null ? null : location.ordinal());
        ProgressRecord.TimePeriod time = record.getTimePeriod();
        values.put(ProgressDBHandler.COL_REC_TIME, time == null ? null : time.ordinal());

        return values;
    }

    private static ProgressRecord mapCursorToRecord(Cursor cursor) {
        // Create a new progress record from the (non-null) Cursor
        ProgressRecord record = new ProgressRecord();

        long id = cursor.getLong(cursor.getColumnIndex(ProgressDBHandler.COL_REC_ID));
        record.setId(id);

        Calendar date = Calendar.getInstance();
        long millis = cursor.getLong(cursor.getColumnIndex(ProgressDBHandler.COL_REC_DATE));
        date.setTimeInMillis(millis);
        record.setDate(date);

        int victory = cursor.getInt(cursor.getColumnIndex(ProgressDBHandler.COL_REC_VICTORY));
        record.setVictory(victory == 1);

        int moodIndex = cursor.getInt(cursor.getColumnIndex(ProgressDBHandler.COL_REC_MOOD));
        record.setMood(ProgressRecord.Mood.values()[moodIndex]);

        int locationIndex = cursor.getInt(cursor.getColumnIndex(ProgressDBHandler.COL_REC_LOCATION));
        record.setLocation(ProgressRecord.Location.values()[locationIndex]);

        int timeIndex = cursor.getInt(cursor.getColumnIndex(ProgressDBHandler.COL_REC_TIME));
        record.setTimePeriod(ProgressRecord.TimePeriod.values()[timeIndex]);

        return record;
    }
}

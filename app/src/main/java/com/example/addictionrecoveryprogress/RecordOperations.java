package com.example.addictionrecoveryprogress;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class RecordOperations {
  private static long _nextId = 1;

  private List<ProgressRecord> _records;

  RecordOperations(Context context) {
    _records = new ArrayList<>();
  }

  void open() {
  }

  void close() {
  }

  ProgressRecord addRecord(ProgressRecord record) {
    record.setId(_nextId);
    _nextId++;
    _records.add(record);
    return record;
  }

  ProgressRecord getRecord(long id) {
    for (ProgressRecord record : _records) {
      if (id == record.getId()) {
        return record;
      }
    }
    return null;
  }

  ProgressRecord getRecord(Calendar date) {
    for (ProgressRecord record : _records) {
      if ((date == null && record.getDate() == null) ||
          (date != null && record.getDate() != null &&
              date.get(Calendar.DATE) == record.getDate().get(Calendar.DATE))) {
        return record;
      }
    }
    return null;
  }

  List<ProgressRecord> getAllRecords() {
    return _records;
  }

  int updateRecord(ProgressRecord record) {
    for (int i = 0; i < _records.size(); i++) {
      ProgressRecord oldRecord = _records.get(i);
      if (oldRecord.getId() == record.getId()) {
        _records.set(i, record);
        return 1;
      }
    }
    return 0;
  }
}

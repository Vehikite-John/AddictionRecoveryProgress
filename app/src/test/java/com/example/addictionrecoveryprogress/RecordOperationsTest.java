package com.example.addictionrecoveryprogress;


import android.annotation.SuppressLint;
import android.content.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class RecordOperationsTest {
  private static RecordOperations op;

  @SuppressLint("StaticFieldLeak")
  @Mock
  static Context mContext;

  @BeforeClass
  public static void setUp() {
    op = new RecordOperations(mContext);
    op.open();
  }

  @AfterClass
  public static void cleanUp() {
    op.close();
  }

  @Test
  public void testAddRecord() {
    ProgressRecord record = new ProgressRecord();
    record.setDate(Calendar.getInstance());

    ProgressRecord addedRecord = op.addRecord(record);
    assertEquals(record.getDate().get(Calendar.DATE), addedRecord.getDate().get(Calendar.DATE));
  }

  @Test
  public void testGetRecordByDate() {
    Calendar date = Calendar.getInstance();
    date.set(2016, 1, 14);

    ProgressRecord record = new ProgressRecord();
    record.setDate(date);
    op.addRecord(record);

    record = op.getRecord(date);
    assertNotEquals(null, record);
    assertEquals(date.get(Calendar.DATE), record.getDate().get(Calendar.DATE));
  }

  @Test
  public void testGetRecordById() {
    ProgressRecord record = new ProgressRecord();
    record = op.addRecord(record);
    long id = record.getId();

    record = op.getRecord(id);
    assertNotEquals(null, record);
    assertEquals(id, record.getId());
  }

  @Test
  public void testUpdateRecord() {
    ProgressRecord record = new ProgressRecord();
    record = op.addRecord(record);
    long id = record.getId();

    Calendar date = Calendar.getInstance();
    date.set(2016, 2, 10);
    record.setDate(date);
    int count = op.updateRecord(record);
    assertEquals(1, count);
    record = op.getRecord(id);
    assertEquals(date.get(Calendar.DATE), record.getDate().get(Calendar.DATE));
  }

  @Test
  public void testGetAllRecords() {
    ProgressRecord record = new ProgressRecord();
    record = op.addRecord(record);
    long id = record.getId();

    List<ProgressRecord> records = op.getAllRecords();
    int matchCount = 0;
    for (ProgressRecord r : records) {
      if (r.getId() == id) {
        matchCount++;
      }
    }
    assertEquals(1, matchCount);
  }
}

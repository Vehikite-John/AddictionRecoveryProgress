package com.example.addictionrecoveryprogress;


import android.content.Context;

import org.junit.After;
import org.junit.Before;
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
  private RecordOperations op;

  @Mock
  Context mContext;

  @Before
  public void setUp() {
    op = new RecordOperations(mContext);
    op.open();
  }

  @After
  public void cleanUp() {
    op.close();
  }

  @Test
  public void testAddRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record.setDate(Calendar.getInstance());

    ProgressRecord addedRecord = op.addRecord(record);
    assertEquals(record.getDate().getTimeInMillis(), addedRecord.getDate().getTimeInMillis());
  }

  @Test
  public void testGetRecordByDate() throws Exception {
    Calendar date = Calendar.getInstance();
    date.set(2016, 1, 14);

    ProgressRecord record = new ProgressRecord();
    record.setDate(date);
    op.addRecord(record);

    record = op.getRecord(date);
    assertNotEquals(null, record);
    assertEquals(date.getTimeInMillis(), record.getDate().getTimeInMillis());
  }

  @Test
  public void testGetRecordById() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record = op.addRecord(record);
    long id = record.getId();

    record = op.getRecord(id);
    assertNotEquals(null, record);
    assertEquals(id, record.getId());
  }

  @Test
  public void testUpdateRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record = op.addRecord(record);
    long id = record.getId();

    Calendar date = Calendar.getInstance();
    date.set(2016, 2, 10);
    record.setDate(date);
    int count = op.updateRecord(record);
    assertEquals(1, count);
    record = op.getRecord(id);
    assertEquals(date.getTimeInMillis(), record.getDate().getTimeInMillis());
  }

  @Test
  public void testGetAllRecords() throws Exception {
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

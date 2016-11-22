package com.example.addictionrecoveryprogress;


import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RecordOperationsTest {
  private RecordOperations tOp;
  private Context tContext;

  private void initDbForStatsTests() {
    boolean victories[] = new boolean[]{
        true, true, true, false, false, true, true,
        true, true, false, true, false, false, true,
        true, true, true, true, true, true, true,
        false, false, false, true, true, true, true,
        true, true, false, true, false, false, true};
    Calendar date = Calendar.getInstance();
    date.set(2016, Calendar.MARCH, 1);
    ProgressRecord record = new ProgressRecord();
    for (boolean victory : victories) {
      record.setVictory(victory);
      record.setDate(date);
      tOp.addRecord(record);
      date.add(Calendar.DAY_OF_MONTH, 1);
    }
  }

  @Before
  public void setUp() {
    tContext = RuntimeEnvironment.application;
    tOp = new RecordOperations(tContext);
    tOp.open();
  }

  @After
  public void cleanUp() {
    tOp.close();
  }

  @Test
  public void testAddRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record.setDate(Calendar.getInstance());

    ProgressRecord addedRecord = tOp.addRecord(record);
    assertEquals(record.getDate().getTimeInMillis(), addedRecord.getDate().getTimeInMillis());
  }

  @Test
  public void testGetRecordByDate() throws Exception {
    Calendar date = Calendar.getInstance();
    date.set(2016, Calendar.JANUARY, 14);
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);

    ProgressRecord record = new ProgressRecord();
    record.setDate(date);
    tOp.addRecord(record);

    record = tOp.getRecord(date);
    assertNotEquals(null, record);
    assertEquals(date.getTimeInMillis(), record.getDate().getTimeInMillis());
  }

  @Test
  public void testGetRecordById() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record = tOp.addRecord(record);
    long id = record.getId();

    record = tOp.getRecord(id);
    assertNotEquals(null, record);
    assertEquals(id, record.getId());
  }

  @Test
  public void testUpdateRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record = tOp.addRecord(record);
    long id = record.getId();

    Calendar date = Calendar.getInstance();
    date.set(2016, Calendar.FEBRUARY, 10);
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);

    record.setDate(date);
    int count = tOp.updateRecord(record);
    assertEquals(1, count);
    record = tOp.getRecord(id);
    assertEquals(date.getTimeInMillis(), record.getDate().getTimeInMillis());
  }

  @Test
  public void testGetAllRecords() throws Exception {
    ProgressRecord record = new ProgressRecord();
    record = tOp.addRecord(record);
    long id = record.getId();

    List<ProgressRecord> records = tOp.getAllRecords();
    int matchCount = 0;
    for (ProgressRecord r : records) {
      if (r.getId() == id) {
        matchCount++;
      }
    }
    assertEquals(1, matchCount);
  }

  @Test
  public void testGetRecordByBadId() throws Exception {
    ProgressRecord record = tOp.getRecord(-1);
    assertEquals(null, record);
  }

  @Test
  public void testGetRecordByBadDate() throws Exception {
    Calendar date = Calendar.getInstance();
    date.set(2016, Calendar.JANUARY, 14);
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);

    ProgressRecord record = tOp.getRecord(date);
    assertEquals(null, record);
  }

  @Test
  public void testGetLongestStreak() throws Exception {
    initDbForStatsTests();
    int streak = tOp.getLongestStreak();
    assertEquals(8, streak);
  }

  @Test
  public void testGetCurrentStreak() throws Exception {
    initDbForStatsTests();
    int streak = tOp.getCurrentStreak();
    assertEquals(1, streak);
  }

  @Test
  public void testGetTotalVictories() throws Exception {
    initDbForStatsTests();
    int count = tOp.getTotalVictories();
    assertEquals(24, count);
  }

  @Test
  public void testGetMonthVictories() throws Exception {
    initDbForStatsTests();
    int count = tOp.getMonthVictories();
    assertEquals(0, count);
  }

  @Test
  public void testGetTotalVictoriesPercent() throws Exception {
    initDbForStatsTests();
    int percent = tOp.getTotalVictoriesPercent();
    assertEquals(68, percent);
  }

  @Test
  public void testGetMonthVictoriesPercent() throws Exception {
    initDbForStatsTests();
    int percent = tOp.getMonthVictoriesPercent();
    assertEquals(0, percent);
  }
}

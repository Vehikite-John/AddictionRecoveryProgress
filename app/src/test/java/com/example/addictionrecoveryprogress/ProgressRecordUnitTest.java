package com.example.addictionrecoveryprogress;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for ProgressRecord class
 */
public class ProgressRecordUnitTest {
  @Test
  public void testSetVictory() throws Exception {
    ProgressRecord pr = new ProgressRecord();
    pr.setDate(new GregorianCalendar());

    pr.setVictory(true);
    assertEquals(pr.isVictory(), true);

    pr.setVictory(false);
    assertEquals(pr.isVictory(), false);
  }

  @Test
  public void testSetMood() throws Exception {
    ProgressRecord pr = new ProgressRecord();
    pr.setDate(new GregorianCalendar());

    for (ProgressRecord.Mood mood : ProgressRecord.Mood.values()) {
      pr.setMood(mood);
      assertEquals(pr.getMood(), mood);
    }
  }

  @Test
  public void testSetLocation() throws Exception {
    ProgressRecord pr = new ProgressRecord();
    pr.setDate(new GregorianCalendar());

    for (ProgressRecord.Location location : ProgressRecord.Location.values()) {
      pr.setLocation(location);
      assertEquals(pr.getLocation(), location);
    }
  }

  @Test
  public void testSetTimePeriod() throws Exception {
    ProgressRecord pr = new ProgressRecord();
    pr.setDate(new GregorianCalendar());

    for (ProgressRecord.TimePeriod period : ProgressRecord.TimePeriod.values()) {
      pr.setTimePeriod(period);
      assertEquals(pr.getTimePeriod(), period);
    }
  }

  @Test
  public void testGetDate() throws Exception {
    Calendar date = new GregorianCalendar();
    ProgressRecord pr = new ProgressRecord();
    pr.setDate(date);

    // Ensure that only the year, month and day are set; other fields are set to zero.
    assertEquals(pr.getDate().get(Calendar.YEAR), date.get(Calendar.YEAR));
    assertEquals(pr.getDate().get(Calendar.MONTH), date.get(Calendar.MONTH));
    assertEquals(pr.getDate().get(Calendar.DAY_OF_MONTH), date.get(Calendar.DAY_OF_MONTH));
    assertEquals(pr.getDate().get(Calendar.HOUR_OF_DAY), 0);
    assertEquals(pr.getDate().get(Calendar.HOUR), 0);
    assertEquals(pr.getDate().get(Calendar.MINUTE), 0);
    assertEquals(pr.getDate().get(Calendar.SECOND), 0);
    assertEquals(pr.getDate().get(Calendar.MILLISECOND), 0);
  }
}

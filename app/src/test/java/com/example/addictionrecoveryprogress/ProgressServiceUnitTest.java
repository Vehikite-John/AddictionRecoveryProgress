package com.example.addictionrecoveryprogress;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class ProgressServiceUnitTest implements Observer {
  private boolean _ready = false;

  public void update(Observable subject, String property) {
    _ready = true;
  }

  @Test
  public void testSubmitRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    ProgressService service = new ProgressService();
    service.submitRecord(record);
    service.selectRecord(record.getDate());
    for (int i = 0; i < 2; i++) {
      if (!_ready) {
        Thread.sleep(1000);
      } else {
        break;
      }
    }
    assertEquals(_ready, true);
    ProgressRecord otherRecord = service.getRecord();
    assertEquals(record.getDate(), otherRecord.getDate());
  }

  @Test
  public void testSelectRecord() throws Exception {
    ProgressRecord record = new ProgressRecord();
    ProgressService service = new ProgressService();
    service.submitRecord(record);
    record.getDate().add(Calendar.DATE, -1);
    service.submitRecord(record);
    service.selectRecord(record.getDate());
    for (int i = 0; i < 2; i++) {
      if (!_ready) {
        Thread.sleep(1000);
      } else {
        break;
      }
    }
    assertEquals("second record ready", _ready, true);
    ProgressRecord otherRecord = service.getRecord();
    assertEquals(record.getDate(), otherRecord.getDate());

    record.getDate().add(Calendar.DATE, 1);
    service.selectRecord(record.getDate());
    for (int i = 0; i < 2; i++) {
      if (!_ready) {
        Thread.sleep(1000);
      } else {
        break;
      }
    }
    assertEquals("first record ready", _ready, true);
    otherRecord = service.getRecord();
    assertEquals(record.getDate(), otherRecord.getDate());
  }
}

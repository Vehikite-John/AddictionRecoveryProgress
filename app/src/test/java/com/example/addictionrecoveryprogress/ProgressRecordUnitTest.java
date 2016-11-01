package com.example.addictionrecoveryprogress;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for ProgressRecord class
 */
public class ProgressRecordUnitTest {
  @Test
  public void testSetVictory() throws Exception {
    ProgressRecord pr = new ProgressRecord(new Date());

    pr.setVictory(true);
    assertEquals(pr.isVictory(), true);

    pr.setVictory(false);
    assertEquals(pr.isVictory(), false);
  }
}

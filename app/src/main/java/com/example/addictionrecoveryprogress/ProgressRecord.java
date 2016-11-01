package com.example.addictionrecoveryprogress;

import java.util.Date;

/**
 * The record of one day's addiction-recovery progress
 */
class ProgressRecord {
  private Date _date;

  ProgressRecord(Date date) {
    _date = date;
  }

  /**
   * Whether the day counts as a 'victory' over addiction.
   *
   * @return true if day was a victory; false if day was a setback.
   */
  boolean isVictory() {
    return false;
  }

  /**
   * Sets the day's status as a victory over addiction or as a setback.
   *
   * @param status true if the day was a victory; false if the day was a setback.
   */
  void setVictory(boolean status) {
  }

}

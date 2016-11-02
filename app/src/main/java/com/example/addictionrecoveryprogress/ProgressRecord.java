package com.example.addictionrecoveryprogress;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The record of one day's addiction-recovery progress
 */
class ProgressRecord {
  private Calendar _date;
  private boolean _victory;
  private Mood _mood;
  private Location _location;
  private TimePeriod _timePeriod;

  enum Mood {
    STRESSED,
    BORED,
    SAD,
    ANGRY,
    LONELY
  }

  enum Location {
    HOME_BEDROOM,
    HOME_BATHROOM,
    HOME_OFFICE,
    WORK,
    TRANSPORTATION,
    ACCOMODATION
  }

  enum TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT
  }

  /**
   * Default progress record constructor. Initializes date to yesterday.
   */
  ProgressRecord() {
    this(GregorianCalendar.getInstance());
    _date.add(Calendar.DATE, -1);
  }

  /**
   * Constructs progress record for a specific date.
   *
   * @param date the date of the progress record.
   */
  ProgressRecord(Calendar date) {
    _date = date;

    // ensure date is rounded down to start of day.
    _date.set(Calendar.HOUR_OF_DAY, 0);
    _date.set(Calendar.MINUTE, 0);
    _date.set(Calendar.SECOND, 0);
    _date.set(Calendar.MILLISECOND, 0);
  }

  /**
   * Whether the day counts as a 'victory' over addiction.
   *
   * @return true if day was a victory; false if day was a setback.
   */
  boolean isVictory() {
    return _victory;
  }

  /**
   * Sets the day's status as a victory over addiction or as a setback.
   *
   * @param status true if the day was a victory; false if the day was a setback.
   */
  void setVictory(boolean status) {
    _victory = status;
  }

  /**
   * Retrieves the recorded mood at the time of a setback.
   *
   * @return mood at the time of a setback.
   */
  Mood getMood() {
    return _mood;
  }

  /**
   * Sets the mood at the time of a setback.
   *
   * @param mood how you were feeling at the time of a setback.
   */
  void setMood(Mood mood) {
    _mood = mood;
  }

  /**
   * Retrieves the recorded location at the time of a setback
   *
   * @return the location of the setback
   */
  Location getLocation() {
    return _location;
  }

  /**
   * Sets the location at the time of a setback
   *
   * @param location where you were at the time of a setback.
   */
  void setLocation(Location location) {
    _location = location;
  }

  /**
   * Retrieves the recorded time period of a setback.
   *
   * @return the time when the setback occurred.
   */
  TimePeriod getTimePeriod() {
    return _timePeriod;
  }

  /**
   * Sets the time period of a setback
   *
   * @param timePeriod when the setback occurred.
   */
  void setTimePeriod(TimePeriod timePeriod) {
    _timePeriod = timePeriod;
  }

  /**
   * Returns date of this record
   *
   * @return the date of with this progress record
   */
  Calendar getDate() {
    return _date;
  }

}

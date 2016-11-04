package com.example.addictionrecoveryprogress;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * The record of one day's addiction-recovery progress
 */
class ProgressRecord {
  private long _id;
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
   * Default constructor
   */
  ProgressRecord() {
  }

  /**
   * Non-Default constructor
   */
  ProgressRecord(long recordID, Calendar date, boolean victory, Mood mood, Location location,
                 TimePeriod timePeriod) {
    _id = recordID;
    _date = date;
    _victory = victory;
    _mood = mood;
    _location = location;
    _timePeriod = timePeriod;
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

  /**
   * Sets the progress record date
   *
   * @param date day of progress record
   */
  void setDate(Calendar date) {
    _date = date;

    // ensure date is rounded down to start of day.
    _date.set(Calendar.HOUR_OF_DAY, 0);
    _date.set(Calendar.MINUTE, 0);
    _date.set(Calendar.SECOND, 0);
    _date.set(Calendar.MILLISECOND, 0);
  }

  /**
   * Returns unique progress record ID
   *
   * @return progress record ID
   */
  long getId() {
    return _id;
  }

  /** Sets unique progress record ID
   *
   * @param id unique progress record ID
   */
  void setId(long id) {
    _id = id;
  }

  /** Returns string representation of a progress record
   *
   * @return string representation of progress record
   */
  public String toString(){
    return "Record ID: " + getId() + "\n" +
        "Date: " + (getDate() == null ? "null" :
        DateFormat.getDateInstance().format(getDate().getTime())) + "\n" +
            (isVictory() ? "Victory! Keep it up! " : "Setback, Don't get discouraged ") + "\n";

  }
}

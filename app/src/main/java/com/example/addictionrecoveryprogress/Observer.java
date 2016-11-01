package com.example.addictionrecoveryprogress;

/**
 * The observer role in the Observer pattern
 */
interface Observer {
  /**
   * Called by the Observable subject when subject's state has changed.
   *
   * @param subject  The object under observation
   * @param property The property of the observed object that has changed.
   */
  void update(Observable subject, String property);
}

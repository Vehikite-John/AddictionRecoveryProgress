package com.example.addictionrecoveryprogress;

/**
 * The observed subject role in the Observable pattern.
 */
interface Observable {
  /**
   * Add an observer of the given property of the observable object.
   *
   * @param observer The object monitoring this object's state changes.
   * @param property The aspect of the object state under observation.
   */
  void addObserver(Observer observer, String property);

  /**
   * Remove an observer of the given property of the observable object.
   *
   * @param observer The object monitoring this object's state changes.
   * @param property The aspect of the object state under observation.
   */
  void removeObserver(Observer observer, String property);

  /**
   * Called by the Observable object when the given aspect of the object state has changed.
   *
   * @param property The aspect of the object state that has changed.
   */
  void notifyObservers(String property);
}

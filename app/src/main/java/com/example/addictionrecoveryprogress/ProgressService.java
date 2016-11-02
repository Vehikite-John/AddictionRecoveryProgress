package com.example.addictionrecoveryprogress;

import java.util.Calendar;

class ProgressService implements Observable {
  private ProgressRecord _current = null;

  public void addObserver(Observer observer, String property) {
  }

  public void removeObserver(Observer observer, String property) {

  }

  public void notifyObservers(String property) {
  }

  ProgressRecord getRecord() {
    return null;
  }

  void selectRecord(Calendar date) {

  }

  void submitRecord(ProgressRecord record) {
    _current = record;
    notifyObservers(null);
  }
}

package com.example.addictionrecoveryprogress;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by jdavet on 11/1/2016.
 */

public class MotivationalTextService implements Observable {

    private List<MotivationalText> _texts;
    private ListIterator<MotivationalText> _current;
    private boolean _skipBuiltIns;

    @Override
    public void addObserver(Observer observer, String property) {

    }

    @Override
    public void removeObserver(Observer observer, String property) {

    }

    @Override
    public void notifyObservers(String property) {

    }

    public void addUserText(String text) {

    }

    public void advanceText() {

    }

    public String getCurrentText() {
        return null;
    }
}

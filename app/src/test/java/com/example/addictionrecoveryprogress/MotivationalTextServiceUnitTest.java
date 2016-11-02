package com.example.addictionrecoveryprogress;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by jdavet on 11/1/2016.
 */

public class MotivationalTextServiceUnitTest implements Observer {
    private boolean _ready = false;

    @Test
    public void testAddUserText() throws Exception {
        String text = "This is my text";
        MotivationalTextService mts = new MotivationalTextService();
        mts.addUserText(text);
        mts.advanceText();
        mts.getCurrentText();
        for (int i = 0; i < 2; i++) {
            if (!_ready) {
                Thread.sleep(1000);
            } else {
                break;
            }
        }
        assertEquals(true, _ready);
        assertEquals(text, mts.getCurrentText());
    }

    public void advanceTextTest() throws Exception {
        String text = "This is my text";
        MotivationalTextService mts = new MotivationalTextService();
        mts.addUserText(text);
        mts.advanceText();
        mts.getCurrentText();
        for (int i = 0; i < 2; i++) {
            if (!_ready) {
                Thread.sleep(1000);
            } else {
                break;
            }
        }
        assertEquals(true, _ready);
        assertNotEquals(text, mts.getCurrentText());
    }

    @Override
    public void update(Observable subject, String property) {
        _ready = true;
    }
}

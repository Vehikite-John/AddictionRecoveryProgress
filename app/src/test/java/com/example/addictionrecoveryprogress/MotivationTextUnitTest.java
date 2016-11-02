package com.example.addictionrecoveryprogress;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas on 11/1/2016.
 */

public class MotivationTextUnitTest {
    @Test
    public void testConstructor() throws Exception {
        MotivationalText text = new MotivationalText("This is motivational", true);
        assertEquals("This is motivational", text.getText());
    }

    @Test
    public void testBuiltIn() throws Exception {
        MotivationalText text = new MotivationalText("This is motivational", true);
        assertEquals(true, text.isBuiltIn());
    }


}

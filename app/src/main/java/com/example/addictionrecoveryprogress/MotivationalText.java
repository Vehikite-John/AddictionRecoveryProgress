package com.example.addictionrecoveryprogress;

/**
 * Created by Thomas on 11/1/2016.
 */

public class MotivationalText {


    private String _text;
    private boolean _isBuiltIn;

    public String getText() {
        return _text;
    }

    public boolean isBuiltIn() {
        return false;
    }

    public MotivationalText(String _text, boolean _isBuiltIn) {
        this._text = _text;
        this._isBuiltIn = _isBuiltIn;
    }
}

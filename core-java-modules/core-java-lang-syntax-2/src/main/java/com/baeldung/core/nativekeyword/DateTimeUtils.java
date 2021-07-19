package com.baeldung.core.nativekeyword;

public class DateTimeUtils {

    public native String getSystemTime();

    static {
        System.loadLibrary("nativedatetimeutils");
    }
}

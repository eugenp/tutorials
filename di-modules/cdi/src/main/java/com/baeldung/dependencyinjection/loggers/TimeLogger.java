package com.baeldung.dependencyinjection.loggers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeLogger {
    
    private final SimpleDateFormat dateFormat;
    private final Calendar calendar;
    
    public TimeLogger(SimpleDateFormat dateFormat, Calendar calendar) {
        this.dateFormat = dateFormat;
        this.calendar = calendar;
    }
    
    public String getTime() {
        return dateFormat.format(calendar.getTime());
    }
}
package com.baeldung.contextsdependencyinjection.factories;

import com.baeldung.contextsdependencyinjection.loggers.TimeLogger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.enterprise.inject.Produces;

public class TimeLoggerFactory {
    
    @Produces
    public TimeLogger getTimeLogger() {
        return new TimeLogger(new SimpleDateFormat("HH:mm"), Calendar.getInstance());
    }
}
package com.baeldung.datetime;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class UseLocalTime {
    
    public LocalTime getLocalTimeUsingFactoryOfMethod(int hour, int min, int seconds){        
        LocalTime localTime = LocalTime.of(hour, min, seconds);
        return localTime;
    }
    
    public LocalTime getLocalTimeUsingParseMethod(String timeRepresentation){        
        LocalTime localTime = LocalTime.parse(timeRepresentation);
        return localTime;
    }
    
    public LocalTime getLocalTimeFromClock(){        
        LocalTime localTime = LocalTime.now();
        return localTime;
    }
    
    public LocalTime addAnHour(LocalTime localTime){
        LocalTime newTime = localTime.plus(1,ChronoUnit.HOURS);
        return newTime;
    }
    
    public int getHourFromLocalTime(LocalTime localTime){        
        return localTime.getHour();
    }
    
    public LocalTime getLocalTimeWithMinuteSetToValue(LocalTime localTime, int minute){        
        return localTime.withMinute(minute);
    }
}

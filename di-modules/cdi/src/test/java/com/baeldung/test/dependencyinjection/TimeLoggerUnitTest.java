package com.baeldung.test.dependencyinjection;

import com.baeldung.dependencyinjection.loggers.TimeLogger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TimeLoggerUnitTest {
    
    
    @Test
    public void givenTimeLoggerInstance_whenCalledgetLogTime_thenOneAssertion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        TimeLogger timeLogger = new TimeLogger(dateFormat, calendar);
        String currentTime = dateFormat.format(calendar.getTime());
        assertThat(timeLogger.getTime()).isEqualTo(currentTime);
    }
}
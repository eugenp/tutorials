package com.baeldung.formatduration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.Period;
import org.junit.Test;

public class FormatDurationUnitTest {


    @Test
    public void givenInterval_WhenFormatInterval_formatDuration() {
        long HH = TimeUnit.MILLISECONDS.toHours(38114000);
        long MM = TimeUnit.MILLISECONDS.toMinutes(38114000) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(38114000) % 60;
        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);

        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }

    @Test
    public void givenIntMinutes_WhenConvertUsingTimeUnit_formatHHMM() {
        int totalMinutes = 155; // Example: 2 hours and 35 minutes

        // 1. Calculate the total hours component
        long hours = TimeUnit.MINUTES.toHours(totalMinutes); 

        // 2. Calculate the remaining minutes (total minutes minus the minutes consumed by the hours)
        long remainingMinutes = totalMinutes - TimeUnit.HOURS.toMinutes(hours);

        // 3. Format the result to "hh:mm", ensuring two-digit zero padding (%02d)
        String timeInHHMM = String.format("%02d:%02d", hours, remainingMinutes);

        // Assertions for different scenarios
        assertThat(timeInHHMM).isEqualTo("02:35"); // 155 minutes
    }

    @Test
    public void givenInterval_WhenFormatUsingDuration_formatDuration() {
        Duration duration = Duration.ofMillis(38114000);
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        String timeInHHMMSS =  String.format("%02d:%02d:%02d", HH, MM, SS);
        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }
    
    @Test
    public void givenInterval_WhenFormatDurationUsingApacheCommons_formatDuration() {
        assertThat(DurationFormatUtils.formatDuration(38114000, "HH:mm:ss"))
          .isEqualTo("10:35:14");
    }

    @Test
    public void givenInterval_WhenFormatDurationUsingJodaTime_formatDuration() {
        org.joda.time.Duration duration = new org.joda.time.Duration(38114000);
        Period period = duration.toPeriod();
        long HH = period.getHours();
        long MM = period.getMinutes();
        long SS = period.getSeconds();

        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }
}

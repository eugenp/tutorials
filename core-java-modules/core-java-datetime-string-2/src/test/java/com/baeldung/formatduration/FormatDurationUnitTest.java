package com.baeldung.formatduration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.Period;
import org.junit.Test;

public class FormatDurationUnitTest {


    @Test
    public void givenInterval_whenFormatInterval_thenFormatDuration() {
        long HH = TimeUnit.MILLISECONDS.toHours(38114000);
        long MM = TimeUnit.MILLISECONDS.toMinutes(38114000) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(38114000) % 60;
        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);

        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }

    @Test
    public void givenIntMinutes_whenConvertUsingTimeUnit_thenFormatHHMM() {
        int totalMinutes = 155;  
        long hours = TimeUnit.MINUTES.toHours(totalMinutes); 
        long remainingMinutes = totalMinutes - TimeUnit.HOURS.toMinutes(hours);
        String timeInHHMM = String.format("%02d:%02d", hours, remainingMinutes);
        assertThat(timeInHHMM).isEqualTo("02:35");  
    }

    @Test
    public void givenInterval_whenFormatUsingDuration_thenFormatDuration() {
        Duration duration = Duration.ofMillis(38114000);
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        String timeInHHMMSS =  String.format("%02d:%02d:%02d", HH, MM, SS);
        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }
    
    @Test
    public void givenInterval_whenFormatDurationUsingApacheCommons_thenFormatDuration() {
        assertThat(DurationFormatUtils.formatDuration(38114000, "HH:mm:ss"))
          .isEqualTo("10:35:14");
    }

    @Test
    public void givenInterval_whenFormatDurationUsingJodaTime_thenFormatDuration() {
        org.joda.time.Duration duration = new org.joda.time.Duration(38114000);
        Period period = duration.toPeriod();
        long HH = period.getHours();
        long MM = period.getMinutes();
        long SS = period.getSeconds();

        String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
        assertThat(timeInHHMMSS).isEqualTo("10:35:14");
    }
}

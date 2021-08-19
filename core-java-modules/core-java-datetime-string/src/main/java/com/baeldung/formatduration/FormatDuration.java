package com.baeldung.formatduration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;

public class FormatDuration {

    public String formatDurationUsingTimeUnit(long interval) {
        long HH = TimeUnit.MILLISECONDS.toHours(interval);
        long MM = TimeUnit.MILLISECONDS.toMinutes(interval) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(interval) % 60;

        return String.format("%02d:%02d:%02d", HH, MM, SS);

    }

    public String formatDurationUsingJodaTime(long intervalInMs) {
        org.joda.time.Duration duration = new org.joda.time.Duration(intervalInMs);
        Period period = duration.toPeriod();

        long HH = period.getHours();
        long MM = period.getMinutes();
        long SS = period.getSeconds();

        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

    public String formatUsingDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;

        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

}

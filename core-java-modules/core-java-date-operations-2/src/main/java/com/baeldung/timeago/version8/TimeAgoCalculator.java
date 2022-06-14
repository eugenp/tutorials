package com.baeldung.timeago.version8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

public class TimeAgoCalculator {

    public static String calculateTimeAgoWithPeriodAndDuration(LocalDateTime pastTime, ZoneId zone) {
        Period period = Period.between(pastTime.toLocalDate(), LocalDate.now(zone));
        Duration duration = Duration.between(pastTime, LocalDateTime.now(zone));
        if (period.getYears() != 0)
            return "several years ago";
        else if (period.getMonths() != 0)
            return "several months ago";
        else if (period.getDays() != 0)
            return "several days ago";
        else if (duration.toHours() != 0)
            return "several hours ago";
        else if (duration.toMinutes() != 0)
            return "several minutes ago";
        else if (duration.getSeconds() != 0)
            return "several seconds ago";
        else
            return "moments ago";
    }

    public static String calculateTimeAgoWithPrettyTime(Date pastTime) {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(pastTime);
    }

    public static String calculateTimeAgoWithTime4J(Date pastTime, ZoneId zone, Locale locale) {
        return net.time4j.PrettyTime.of(locale)
            .printRelative(pastTime.toInstant(), zone);
    }

}

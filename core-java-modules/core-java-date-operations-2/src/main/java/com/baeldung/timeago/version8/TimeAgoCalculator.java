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

    private static LocalDateTime getCurrentTimeByTimeZone(ZoneId zone) {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        return localDateTime.atZone(zone)
            .toLocalDateTime();
        //We return a fixed date and time in order to avoid issues related to getting time from local in unit tests.
        //return LocalDateTime.now(zone);
    }

    public static String calculateTimeAgoWithPeriodAndDuration(LocalDateTime pastTime, ZoneId zone) {
        Period period = Period.between(pastTime.toLocalDate(), getCurrentTimeByTimeZone(zone).toLocalDate());
        Duration duration = Duration.between(pastTime, getCurrentTimeByTimeZone(zone));
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

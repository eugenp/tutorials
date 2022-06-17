package com.baeldung.timeago.version7;

import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeAgoCalculator {

    public static String calculateTimeAgoByTimeGranularity(Date pastTime, TimeGranularity granularity) {
        Date currentTime = new Date();
        long timeDifferenceInMillis = currentTime.getTime() - pastTime.getTime();
        return timeDifferenceInMillis / granularity.toMillis() + " " + granularity.name()
            .toLowerCase() + " ago";
    }

    public static String calculateHumanFriendlyTimeAgo(Date pastTime) {
        Date currentTime = new Date();
        long timeDifferenceInMillis = currentTime.getTime() - pastTime.getTime();
        if (timeDifferenceInMillis / TimeGranularity.DECADES.toMillis() > 0)
            return "several decades ago";
        else if (timeDifferenceInMillis / TimeGranularity.YEARS.toMillis() > 0)
            return "several years ago";
        else if (timeDifferenceInMillis / TimeGranularity.MONTHS.toMillis() > 0)
            return "several months ago";
        else if (timeDifferenceInMillis / TimeGranularity.WEEKS.toMillis() > 0)
            return "several weeks ago";
        else if (timeDifferenceInMillis / TimeGranularity.DAYS.toMillis() > 0)
            return "several days ago";
        else if (timeDifferenceInMillis / TimeGranularity.HOURS.toMillis() > 0)
            return "several hours ago";
        else if (timeDifferenceInMillis / TimeGranularity.MINUTES.toMillis() > 0)
            return "several minutes ago";
        else
            return "moments ago";
    }

    public static String calculateExactTimeAgoWithJodaTime(Date pastTime) {
        Period period = new Period(new DateTime(pastTime.getTime()), new DateTime());
        PeriodFormatter formatter = new PeriodFormatterBuilder().appendYears()
            .appendSuffix(" year ", " years ")
            .appendSeparator("and ")
            .appendMonths()
            .appendSuffix(" month ", " months ")
            .appendSeparator("and ")
            .appendWeeks()
            .appendSuffix(" week ", " weeks ")
            .appendSeparator("and ")
            .appendDays()
            .appendSuffix(" day ", " days ")
            .appendSeparator("and ")
            .appendHours()
            .appendSuffix(" hour ", " hours ")
            .appendSeparator("and ")
            .appendMinutes()
            .appendSuffix(" minute ", " minutes ")
            .appendSeparator("and ")
            .appendSeconds()
            .appendSuffix(" second", " seconds")
            .toFormatter();
        return formatter.print(period);
    }

    public static String calculateHumanFriendlyTimeAgoWithJodaTime(Date pastTime) {
        Period period = new Period(new DateTime(pastTime.getTime()), new DateTime());
        if (period.getYears() != 0)
            return "several years ago";
        else if (period.getMonths() != 0)
            return "several months ago";
        else if (period.getWeeks() != 0)
            return "several weeks ago";
        else if (period.getDays() != 0)
            return "several days ago";
        else if (period.getHours() != 0)
            return "several hours ago";
        else if (period.getMinutes() != 0)
            return "several minutes ago";
        else
            return "moments ago";
    }

    public static String calculateZonedTimeAgoWithJodaTime(Date pastTime, TimeZone zone) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(zone.getID());
        Period period = new Period(new DateTime(pastTime.getTime(), dateTimeZone), new DateTime(dateTimeZone));
        return PeriodFormat.getDefault()
            .print(period);
    }

}

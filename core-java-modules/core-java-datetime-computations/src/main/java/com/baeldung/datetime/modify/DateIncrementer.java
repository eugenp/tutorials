package com.baeldung.datetime.modify;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class DateIncrementer {
    private static final Logger log = Logger.getLogger(DateIncrementer.class.getName());
    private static final int INCREMENT_BY_IN_DAYS = 1;

    public static String addOneDay(String date) {
        return LocalDate
          .parse(date)
          .plusDays(INCREMENT_BY_IN_DAYS)
          .toString();
    }

    public static String addOneDayJodaTime(String date) {
        DateTime dateTime = new DateTime(date);
        return dateTime
          .plusDays(INCREMENT_BY_IN_DAYS)
          .toString("yyyy-MM-dd");
    }

    public static String addOneDayCalendar(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        return sdf.format(c.getTime());
    }

    public static String addOneDayApacheCommons(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date incrementedDate = DateUtils.addDays(sdf.parse(date), 1);
        return sdf.format(incrementedDate);
    }

    public static void main(String[] args) throws ParseException {
        String date = LocalDate
          .now()
          .toString();
        log.info("Current date = " + date);

        String incrementedDateJava8 = DateIncrementer.addOneDay(date);
        log.info("Date incremented by one day using (Java 8): " + incrementedDateJava8);

        String incrementedDateJodaTime = DateIncrementer.addOneDayJodaTime(date);
        log.info("Date incremented by one day using (Joda-Time): " + incrementedDateJodaTime);

        String incrementedDateCalendar = addOneDayCalendar(date);
        log.info("Date incremented by one day using (java.util.Calendar): " + incrementedDateCalendar);

        String incrementedDateApacheCommons = addOneDayApacheCommons(date);
        log.info("Date incremented by one day using (Apache Commons DateUtils): " + incrementedDateApacheCommons);
    }
}

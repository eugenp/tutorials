package com.baeldung.date_migration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class OldApi {
    public void currentTime() {
        Date now = new Date();
    }

    public void specificTime () {
        Date birthDay = new GregorianCalendar(1990, Calendar.DECEMBER, 15).getTime();
    }

    public void extractMonth() {
        int month = new GregorianCalendar().get(Calendar.MONTH);
    }

    public void subtractTime() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        Date fiveHoursBefore = calendar.getTime();
    }

    public void alterField() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        Date inJune = calendar.getTime();
    }

    public void truncate() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date truncated = now.getTime();
    }

    public void convertTimeZone() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("CET"));
        Date centralEastern = calendar.getTime();
    }

    public void getTimeSpan() {
        GregorianCalendar calendar = new GregorianCalendar();
        Date now = new Date();
        calendar.add(Calendar.HOUR, 1);
        Date hourLater = calendar.getTime();
        long elapsed = hourLater.getTime() - now.getTime();
    }

    public void formatAndParse() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        Date parsedDate = dateFormat.parse(formattedDate);
    }

    public void daysInMonth() {
        Calendar calendar = new GregorianCalendar(1990, Calendar.FEBRUARY, 20);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}

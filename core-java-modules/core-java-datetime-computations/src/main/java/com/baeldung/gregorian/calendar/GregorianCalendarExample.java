package com.baeldung.gregorian.calendar;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class GregorianCalendarExample {

   
    public Date setMonth(GregorianCalendar calendar, int amount) {
        calendar.set(Calendar.MONTH, amount);
        return calendar.getTime();
    }

   
    public Date rollAdd(GregorianCalendar calendar, int amount) {
        calendar.roll(GregorianCalendar.MONTH, amount);
        return calendar.getTime();
    }

    public  boolean isLeapYearExample(int year) {
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        return cal.isLeapYear(year);
    }



    public Date subtractDays(GregorianCalendar calendar, int daysToSubtract) {
        GregorianCalendar cal = calendar;
        cal.add(Calendar.DATE, -daysToSubtract);
        return cal.getTime();
    }

    public Date addDays(GregorianCalendar calendar, int daysToAdd) {
        GregorianCalendar cal = calendar;
        cal.add(Calendar.DATE, daysToAdd);
        return cal.getTime();
    }

    public XMLGregorianCalendar toXMLGregorianCalendar(GregorianCalendar calendar) throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        return datatypeFactory.newXMLGregorianCalendar(calendar);
    }

    public Date toDate(XMLGregorianCalendar calendar) {
        return calendar.toGregorianCalendar()
            .getTime();
    }

    public int compareDates(GregorianCalendar firstDate, GregorianCalendar secondDate) {
        return firstDate.compareTo(secondDate);
    }

    public String formatDate(GregorianCalendar calendar) {
        return calendar.toZonedDateTime()
            .format(DateTimeFormatter.ofPattern("d MMM uuuu"));
    }

}

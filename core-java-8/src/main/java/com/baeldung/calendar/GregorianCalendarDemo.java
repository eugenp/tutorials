package com.baeldung.calendar;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class GregorianCalendarDemo {

    public static void main(String[] args) throws DatatypeConfigurationException {

        System.out.println(toXMLGregorianCalendar(new Date()));
        System.out.println(toDate(toXMLGregorianCalendar(new Date())));
        System.out.println(subtractDays(new Date(), -400));
        System.out.println(addDays(new Date(), 40));
        compareDates();
        formatDate();
    }

    public static Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return datatypeFactory.newXMLGregorianCalendar(cal);
    }

    public static Date toDate(XMLGregorianCalendar calendar) {
        return calendar.toGregorianCalendar()
            .getTime();
    }

    public static void compareDates() {
        Calendar user = new GregorianCalendar(2020, Calendar.FEBRUARY, 17);
        Calendar now = new GregorianCalendar();
        System.out.println("Date Difference: " + user.compareTo(now));
    }

    public static void formatDate() {
        GregorianCalendar cal = new GregorianCalendar();
        System.out.println(cal.toZonedDateTime()
            .format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));
    }
}

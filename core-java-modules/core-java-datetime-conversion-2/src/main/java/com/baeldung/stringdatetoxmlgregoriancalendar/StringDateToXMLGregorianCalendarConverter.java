package com.baeldung.stringdatetoxmlgregoriancalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class StringDateToXMLGregorianCalendarConverter {
    public static XMLGregorianCalendar usingDatatypeFactoryForDate(String dateAsString) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateAsString);
    }

    public static XMLGregorianCalendar usingLocalDate(String dateAsString) throws DatatypeConfigurationException {
        LocalDate localDate = LocalDate.parse(dateAsString);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
    }

    public static XMLGregorianCalendar usingSimpleDateFormat(String dateTimeAsString) throws DatatypeConfigurationException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = simpleDateFormat.parse(dateTimeAsString);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(simpleDateFormat.format(date));
    }

    public static XMLGregorianCalendar usingGregorianCalendar(String dateTimeAsString) throws DatatypeConfigurationException, ParseException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTimeAsString));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }

    public static XMLGregorianCalendar usingJodaTime(String dateTimeAsString) throws DatatypeConfigurationException {
        DateTime dateTime = DateTime.parse(dateTimeAsString, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime.toGregorianCalendar());
    }
}

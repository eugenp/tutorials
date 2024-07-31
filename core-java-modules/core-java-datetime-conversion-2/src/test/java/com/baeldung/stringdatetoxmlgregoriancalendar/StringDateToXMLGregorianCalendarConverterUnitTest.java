package com.baeldung.stringdatetoxmlgregoriancalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.Test;

public class StringDateToXMLGregorianCalendarConverterUnitTest {
    private static final String dateAsString = "2014-04-24";
    private static final String dateTimeAsString = "2014-04-24T15:45:30";

    @Test
    void givenStringDate_whenUsingDatatypeFactory_thenConvertToXMLGregorianCalendar() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = StringDateToXMLGregorianCalendarConverter.usingDatatypeFactoryForDate(dateAsString);
        assertEquals(24,xmlGregorianCalendar.getDay());
        assertEquals(4,xmlGregorianCalendar.getMonth());
        assertEquals(2014,xmlGregorianCalendar.getYear());
    }

    @Test
    void givenStringDateTime_whenUsingApacheCommonsLang3_thenConvertToXMLGregorianCalendar() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = StringDateToXMLGregorianCalendarConverter.usingLocalDate(dateAsString);
        assertEquals(24,xmlGregorianCalendar.getDay());
        assertEquals(4,xmlGregorianCalendar.getMonth());
        assertEquals(2014,xmlGregorianCalendar.getYear());
    }

    @Test
    void givenStringDateTime_whenUsingSimpleDateFormat_thenConvertToXMLGregorianCalendar() throws DatatypeConfigurationException, ParseException {
        XMLGregorianCalendar xmlGregorianCalendar = StringDateToXMLGregorianCalendarConverter.usingSimpleDateFormat(dateTimeAsString);
        assertEquals(24,xmlGregorianCalendar.getDay());
        assertEquals(4,xmlGregorianCalendar.getMonth());
        assertEquals(2014,xmlGregorianCalendar.getYear());
        assertEquals(15,xmlGregorianCalendar.getHour());
        assertEquals(45,xmlGregorianCalendar.getMinute());
        assertEquals(30,xmlGregorianCalendar.getSecond());
    }

    @Test
    void givenStringDateTime_whenUsingGregorianCalendar_thenConvertToXMLGregorianCalendar() throws DatatypeConfigurationException, ParseException {
        XMLGregorianCalendar xmlGregorianCalendar = StringDateToXMLGregorianCalendarConverter.usingGregorianCalendar(dateTimeAsString);
        assertEquals(24,xmlGregorianCalendar.getDay());
        assertEquals(4,xmlGregorianCalendar.getMonth());
        assertEquals(2014,xmlGregorianCalendar.getYear());
        assertEquals(15,xmlGregorianCalendar.getHour());
        assertEquals(45,xmlGregorianCalendar.getMinute());
        assertEquals(30,xmlGregorianCalendar.getSecond());
    }

    @Test
     void givenStringDateTime_whenUsingJodaTime_thenConvertToXMLGregorianCalendar() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = StringDateToXMLGregorianCalendarConverter.usingJodaTime(dateTimeAsString);
        assertEquals(24,xmlGregorianCalendar.getDay());
        assertEquals(4,xmlGregorianCalendar.getMonth());
        assertEquals(2014,xmlGregorianCalendar.getYear());
        assertEquals(15,xmlGregorianCalendar.getHour());
        assertEquals(45,xmlGregorianCalendar.getMinute());
        assertEquals(30,xmlGregorianCalendar.getSecond());
    }
}

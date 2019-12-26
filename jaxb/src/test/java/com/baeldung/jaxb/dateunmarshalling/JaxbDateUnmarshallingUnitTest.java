package com.baeldung.jaxb.dateunmarshalling;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class JaxbDateUnmarshallingUnitTest {

    @Test
    public void whenUnmarshalDatesIsCalled_ThenCorrectDateIsReturned() throws JAXBException, DatatypeConfigurationException {
        InputStream inputStream = JaxbDateUnmarshalling.getInputStream(JaxbDateUnmarshalling.DEFAULT_DATE_UNMARSHALLING_FILE);
        XMLGregorianCalendar expected = DatatypeFactory.newInstance().newXMLGregorianCalendar("1979-11-28T02:31:32");

        Book book = JaxbDateUnmarshalling.unmarshalDates(inputStream);

        assertEquals(expected, book.getPublished());
    }

    @Test
    public void whenUnmarshalDatesUsingCustomXmlAdapterIsCalled_ThenCorrectDateIsReturned() throws JAXBException, ParseException {
        InputStream inputStream = JaxbDateUnmarshalling.getInputStream(JaxbDateUnmarshalling.CUSTOM_DATE_UNMARSHALLING_FILE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date expected = format.parse("1979-11-28 02:31:32");

        BookDateAdapter book = JaxbDateUnmarshalling.unmarshalDatesUsingCustomXmlAdapter(inputStream);

        assertEquals(expected, book.getPublished());
    }

    @Test
    public void whenUnmarshalDatesUsingJava8IsCalled_ThenCorrectDateIsReturned() throws JAXBException {
        InputStream inputStream = JaxbDateUnmarshalling.getInputStream(JaxbDateUnmarshalling.CUSTOM_DATE_UNMARSHALLING_FILE);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expected = LocalDateTime.parse("1979-11-28 02:31:32", dateFormat);

        BookLocalDateTimeAdapter book = JaxbDateUnmarshalling.unmarshalDatesUsingJava8(inputStream);

        assertEquals(expected, book.getPublished());
    }

}
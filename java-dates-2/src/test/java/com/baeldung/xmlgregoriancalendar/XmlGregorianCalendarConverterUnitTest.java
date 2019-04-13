package com.baeldung.xmlgregoriancalendar;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlGregorianCalendarConverterUnitTest {

    @Test
    public void fromLocalDateToXMLGregorianCalendar() throws DatatypeConfigurationException {
        LocalDate localDate = LocalDate.of(2017, 4, 25);
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());

        assertThat(xmlGregorianCalendar.getYear()).isEqualTo(localDate.getYear());
        assertThat(xmlGregorianCalendar.getMonth()).isEqualTo(localDate.getMonthValue());
        assertThat(xmlGregorianCalendar.getDay()).isEqualTo(localDate.getDayOfMonth());
        assertThat(xmlGregorianCalendar.getTimezone()).isEqualTo(DatatypeConstants.FIELD_UNDEFINED);
    }

    @Test
    public void fromXMLGregorianCalendarToLocalDate() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2017-04-25");
        LocalDate localDate = LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay());

        assertThat(localDate.getYear()).isEqualTo(xmlGregorianCalendar.getYear());
        assertThat(localDate.getMonthValue()).isEqualTo(xmlGregorianCalendar.getMonth());
        assertThat(localDate.getDayOfMonth()).isEqualTo(xmlGregorianCalendar.getDay());
    }

}

package com.baeldung.xmlgregoriancalendar;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

import static com.baeldung.xmlgregoriancalendar.XmlGregorianCalendarConverter.fromLocalDate;
import static com.baeldung.xmlgregoriancalendar.XmlGregorianCalendarConverter.fromXMLGregorianCalendar;
import static org.assertj.core.api.Assertions.assertThat;

public class XmlGregorianCalendarConverterTest {

    @Test
    public void fromLocalDateToXMLGregorianCalendar() throws DatatypeConfigurationException {
        LocalDate localDate = LocalDate.of(2017, 4, 25);
        XMLGregorianCalendar xmlGregorianCalendar = fromLocalDate(localDate);

        assertThat(xmlGregorianCalendar.getYear()).isEqualTo(localDate.getYear());
        assertThat(xmlGregorianCalendar.getMonth()).isEqualTo(localDate.getMonthValue());
        assertThat(xmlGregorianCalendar.getDay()).isEqualTo(localDate.getDayOfMonth());
    }

    @Test
    public void fromXMLGregorianCalendarToLocalDate() throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2017-04-25");
        LocalDate localDate = fromXMLGregorianCalendar(xmlGregorianCalendar);

        assertThat(localDate.getYear()).isEqualTo(xmlGregorianCalendar.getYear());
        assertThat(localDate.getMonthValue()).isEqualTo(xmlGregorianCalendar.getMonth());
        assertThat(localDate.getDayOfMonth()).isEqualTo(xmlGregorianCalendar.getDay());
    }

    @Test
    public void compositionOfFunctionsIsIdentity() throws DatatypeConfigurationException { // Only if we don't consider time
        LocalDate localDate = LocalDate.of(2017, 4, 25);
        XMLGregorianCalendar xmlGregorianCalendar = fromLocalDate(localDate);
        LocalDate resultDate = fromXMLGregorianCalendar(xmlGregorianCalendar);

        assertThat(resultDate).isEqualTo(localDate);
    }
}

package com.baeldung.xmlgregoriancalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

public class XmlGregorianCalendarConverter {

    public static void main(String[] args) throws DatatypeConfigurationException {
        LocalDate localDate = LocalDate.now();
        System.out.println("localdate: " + localDate);
        XMLGregorianCalendar xmlGregorianCalendar = fromLocalDate(localDate);
        System.out.println("xmlGregorianCalendar: " + xmlGregorianCalendar);

        xmlGregorianCalendar.setTime(1, 1, 30);
        System.out.println("xmlGregorianCalendar with time information: " + xmlGregorianCalendar);
        LocalDate newLocalDate = fromXMLGregorianCalendar(xmlGregorianCalendar);
        System.out.println("newLocalDate: " + newLocalDate);
    }

    static XMLGregorianCalendar fromLocalDate(LocalDate localDate) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
    }

    static LocalDate fromXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay());
    }
}

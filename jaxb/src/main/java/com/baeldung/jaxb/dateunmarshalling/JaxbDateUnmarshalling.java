package com.baeldung.jaxb.dateunmarshalling;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;

public class JaxbDateUnmarshalling {

    public static final String DEFAULT_DATE_UNMARSHALLING_FILE = "default-date-unmarshalling.xml";
    public static final String CUSTOM_DATE_UNMARSHALLING_FILE = "custom-date-unmarshalling.xml";

    public static Book unmarshalDates(InputStream inputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Book) jaxbUnmarshaller.unmarshal(inputFile);
    }

    public static BookDateAdapter unmarshalDatesUsingCustomXmlAdapter(InputStream inputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(BookDateAdapter.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (BookDateAdapter) jaxbUnmarshaller.unmarshal(inputFile);
    }

    public static BookLocalDateTimeAdapter unmarshalDatesUsingJava8(InputStream inputFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(BookLocalDateTimeAdapter.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (BookLocalDateTimeAdapter) jaxbUnmarshaller.unmarshal(inputFile);
    }

    public static InputStream getInputStream(String file) {
        ClassLoader classLoader = JaxbDateUnmarshalling.class.getClassLoader();
        return classLoader.getResourceAsStream(file);
    }

    public static void main(String[] args) throws JAXBException {
        Book book = unmarshalDates(getInputStream(DEFAULT_DATE_UNMARSHALLING_FILE));
        BookDateAdapter bookDateAdapter = unmarshalDatesUsingCustomXmlAdapter(getInputStream(CUSTOM_DATE_UNMARSHALLING_FILE));
        BookLocalDateTimeAdapter bookLocalDateTimeAdapter = unmarshalDatesUsingJava8(getInputStream(CUSTOM_DATE_UNMARSHALLING_FILE));
        System.out.println(book);
        System.out.println(bookDateAdapter);
        System.out.println(bookLocalDateTimeAdapter);
    }

}

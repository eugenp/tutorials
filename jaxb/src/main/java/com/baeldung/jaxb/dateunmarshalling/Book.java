package com.baeldung.jaxb.dateunmarshalling;

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "book")
public class Book {

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "published", required = true)
    private XMLGregorianCalendar published;

    public XMLGregorianCalendar getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "[title: " + title + "; published: " + published.toString() + "]";
    }

}
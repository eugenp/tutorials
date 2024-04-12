package com.baeldung.jaxb.dateunmarshalling;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "book")
public class BookLocalDateTimeAdapter {

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "published", required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime published;

    public LocalDateTime getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "[title: " + title + "; published: " + published.toString() + "]";
    }

}
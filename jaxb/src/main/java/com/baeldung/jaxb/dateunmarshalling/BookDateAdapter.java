package com.baeldung.jaxb.dateunmarshalling;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "book")
public class BookDateAdapter {

    @XmlElement(name = "title", required = true)
    private String title;

    @XmlElement(name = "published", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date published;

    public Date getPublished() {
        return published;
    }

    @Override
    public String toString() {
        return "[title: " + title + "; published: " + published.toString() + "]";
    }

}
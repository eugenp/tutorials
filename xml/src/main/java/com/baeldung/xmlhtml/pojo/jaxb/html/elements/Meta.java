package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Meta {

    private String title;

    public String getTitle() {
        return title;
    }

    @XmlAttribute(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
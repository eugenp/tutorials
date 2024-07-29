package com.baeldung.xmlhtml.pojo.jaxb.xml.elements;

import jakarta.xml.bind.annotation.XmlValue;

public class DescendantThree {

    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import javax.xml.bind.annotation.XmlValue;

public class CustomElement {

    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

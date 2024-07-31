package com.baeldung.xmlhtml.pojo.jaxb.xml.elements;

import jakarta.xml.bind.annotation.XmlElement;

public class DescendantTwo {

    private DescendantThree descendantThree;

    public DescendantThree getDescendantThree() {
        return descendantThree;
    }

    @XmlElement(name = "descendantThree")
    public void setDescendant(DescendantThree descendantThree) {
        this.descendantThree = descendantThree;
    }
}
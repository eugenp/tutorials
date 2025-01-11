package com.baeldung.xmlhtml.pojo.jaxb.xml.elements;

import jakarta.xml.bind.annotation.XmlElement;

public class Ancestor {

    private DescendantOne descendantOne;
    private DescendantTwo descendantTwo;

    public DescendantOne getDescendantOne() {
        return descendantOne;
    }

    @XmlElement(name = "descendantOne")
    public void setDescendantOne(DescendantOne descendantOne) {
        this.descendantOne = descendantOne;
    }

    public DescendantTwo getDescendantTwo() {
        return descendantTwo;
    }

    @XmlElement(name = "descendantTwo")
    public void setDescendantTwo(DescendantTwo descendantTwo) {
        this.descendantTwo = descendantTwo;
    }
}


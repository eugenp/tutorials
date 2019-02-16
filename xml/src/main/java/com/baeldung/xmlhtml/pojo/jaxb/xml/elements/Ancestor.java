package com.baeldung.xmlhtml.pojo.jaxb.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
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


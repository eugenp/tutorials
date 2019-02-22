package com.baeldung.xmlhtml.pojo.jaxb.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
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
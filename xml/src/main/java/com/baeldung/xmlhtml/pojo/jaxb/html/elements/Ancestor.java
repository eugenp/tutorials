package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam I. Gerard
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class Ancestor {

    private Descendant descendant;

    public Descendant getDescendant() {
        return descendant;
    }

    @XmlElement(name = "Descendant")
    public void setDescendant(Descendant descendant) {
        this.descendant = descendant;
    }
}

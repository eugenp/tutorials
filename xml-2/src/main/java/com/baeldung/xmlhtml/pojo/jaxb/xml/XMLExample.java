package com.baeldung.xmlhtml.pojo.jaxb.xml;

import com.baeldung.xmlhtml.pojo.jaxb.xml.elements.Ancestor;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xmlexample")
public class XMLExample {

    private Ancestor ancestor;

    @XmlElement(name = "ancestor")
    public void setAncestor(Ancestor ancestor) {
        this.ancestor = ancestor;
    }

    public Ancestor getAncestor() {
        return ancestor;
    }
}
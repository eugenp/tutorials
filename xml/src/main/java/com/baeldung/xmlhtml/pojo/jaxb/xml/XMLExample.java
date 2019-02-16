package com.baeldung.xmlhtml.pojo.jaxb.xml;

import com.baeldung.xmlhtml.pojo.jaxb.xml.elements.Ancestor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adam I. Gerard
 *
 * Full XML POJO with explicit fields.
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
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
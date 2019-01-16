package com.baeldung.xmlhtml.pojo.jaxb.xml;

import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Descendant;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam I. Gerard
 *
 * Simple XML POJO with hashmap.
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="XmlSimple")
public class XmlSimple {

    private List<Descendant> ancestor = new ArrayList<>();

    public XmlSimple() { }

    public List<Descendant> getAncestor() {
        return ancestor;
    }

    @XmlElementWrapper(name = "Ancestor")
    @XmlElement(name = "Descendant")
    public void setAncestor(List<Descendant> ancestor) {
        this.ancestor = ancestor;
    }
}

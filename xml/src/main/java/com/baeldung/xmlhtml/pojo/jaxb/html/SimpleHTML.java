package com.baeldung.xmlhtml.pojo.jaxb.html;

import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Descendant;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam I. Gerard
 *
 * SimpleHTML POJO.
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name="XmlSimple")
public class SimpleHTML {

    private List<Descendant> ancestor = new ArrayList<>();

    public SimpleHTML() { }

    public List<Descendant> getAncestor() {
        return ancestor;
    }

    @XmlElementWrapper(name = "Ancestor")
    @XmlElement(name = "Descendant")
    public void setAncestor(List<Descendant> ancestor) {
        this.ancestor = ancestor;
    }
}

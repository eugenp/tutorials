package com.baeldung.xmlhtml.pojo.jaxb.xml;

import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Ancestor;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.CustomElement;

import javax.xml.bind.annotation.*;

/**
 * @author Adam I. Gerard
 *
 * Full XML POJO with explicit fields.
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement(name = "XmlFull")
public class XmlFull {

    private Ancestor ancestor;

    private CustomElement customElement;

    public Ancestor getAncestor() {
        return ancestor;
    }

    @XmlElement(name = "Ancestor")
    public void setAncestor(Ancestor ancestor) {
        this.ancestor = ancestor;
    }

    public CustomElement getCustomElement() {
        return customElement;
    }

    @XmlElement(name = "CustomElement")
    public void setCustomElement(CustomElement customElement) {
        this.customElement = customElement;
    }

}
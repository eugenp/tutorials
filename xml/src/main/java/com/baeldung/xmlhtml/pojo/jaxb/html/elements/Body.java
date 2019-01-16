package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class Body {

    private String title;

    private Ancestor ancestor;

    private CustomElement customElement;

    public String getTitle() {
        return title;
    }

    @XmlAttribute(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

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
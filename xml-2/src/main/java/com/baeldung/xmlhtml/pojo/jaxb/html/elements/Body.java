package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import jakarta.xml.bind.annotation.XmlElement;

public class Body {

    private CustomElement customElement;

    public CustomElement getCustomElement() {
        return customElement;
    }

    @XmlElement(name = "p")
    public void setCustomElement(CustomElement customElement) {
        this.customElement = customElement;
    }

    private NestedElement nestedElement;

    public NestedElement getNestedElement() {
        return nestedElement;
    }

    @XmlElement(name = "div")
    public void setNestedElement(NestedElement nestedElement) {
        this.nestedElement = nestedElement;
    }

}
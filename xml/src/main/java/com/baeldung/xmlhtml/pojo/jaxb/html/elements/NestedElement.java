package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import jakarta.xml.bind.annotation.XmlElement;

public class NestedElement {

    private CustomElement customElement;

    public CustomElement getCustomElement() {
        return customElement;
    }

    @XmlElement(name = "p")
    public void setCustomElement(CustomElement customElement) {
        this.customElement = customElement;
    }
}
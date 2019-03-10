package com.baeldung.xmlhtml.pojo.jaxb.html.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType()
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
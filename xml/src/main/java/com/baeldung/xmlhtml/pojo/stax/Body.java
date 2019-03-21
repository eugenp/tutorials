package com.baeldung.xmlhtml.pojo.stax;

public class Body {

    private CustomElement customElement;

    public CustomElement getCustomElement() {
        return customElement;
    }
    public void setCustomElement(CustomElement customElement) {
        this.customElement = customElement;
    }

    private NestedElement nestedElement;

    public NestedElement getNestedElement() {
        return nestedElement;
    }
    public void setNestedElement(NestedElement nestedElement) {
        this.nestedElement = nestedElement;
    }

}
package com.baeldung.deepvsshallowcopy;

import java.util.Objects;

public class ObjectAttribute implements Cloneable {

    private String attributeName;
    private String attributeValue;

    public ObjectAttribute(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObjectAttribute attribute = (ObjectAttribute) o;
        return Objects.equals(attributeName, attribute.attributeName) && Objects.equals(attributeValue, attribute.attributeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, attributeValue);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
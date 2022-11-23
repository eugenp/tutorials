package com.baeldung.deepvsshallowcopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectDescription implements Cloneable {

    private String generalDescription;
    private List<ObjectAttribute> attributes;

    public ObjectDescription(String generalDescription, List<ObjectAttribute> attributes) {
        this.generalDescription = generalDescription;
        this.attributes = attributes;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public List<ObjectAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ObjectAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ObjectDescription description = (ObjectDescription) super.clone();
        description.setAttributes(new ArrayList<>());
        for (ObjectAttribute attribute : attributes) {
            description.getAttributes()
              .add((ObjectAttribute) attribute.clone());
        }
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObjectDescription that = (ObjectDescription) o;
        return Objects.equals(generalDescription, that.generalDescription) && Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generalDescription, attributes);
    }
}
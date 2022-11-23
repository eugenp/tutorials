package com.baeldung.deepvsshallowcopy;

import java.util.Objects;

public class DeepObject implements Cloneable {

    private int id;
    private ObjectDescription description;

    public DeepObject(int id, ObjectDescription description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public ObjectDescription getDescription() {
        return description;
    }

    public void setDescription(ObjectDescription description) {
        this.description = description;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepObject deepObject = (DeepObject) super.clone();
        deepObject.setDescription((ObjectDescription) description.clone());
        return deepObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeepObject that = (DeepObject) o;
        return id == that.id && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
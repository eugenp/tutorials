package com.baeldung.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

public class MyClazz_ implements Cloneable, Serializable {
    private final Map<String, String> properties;

    public MyClazz_(Map<String, String> properties) {
        this.properties = properties;
    }

    public MyClazz_(MyClazz_ myClazz) {
        this.properties = new HashMap<>(myClazz.getProperties());
    }

    public MyClazz_ shallowCopy() {
        try {
            return (MyClazz_) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public MyClazz_ deepCopy() {
        return (MyClazz_) SerializationUtils.clone(this);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}

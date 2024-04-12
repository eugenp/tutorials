package com.baeldung.graphqlreturnmap.model;


import com.baeldung.graphqlreturnmap.entity.Attribute;

public class AttributeKeyValueModel {
    private String key;
    private Attribute value;

    public AttributeKeyValueModel(String key, Attribute value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Attribute getValue() {
        return value;
    }

    public void setValue(Attribute value) {
        this.value = value;
    }
}

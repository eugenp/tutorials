package com.baeldung.jackson.annotation;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ExtendableBean {
    public String name;
    private Map<String, String> properties;

    public ExtendableBean() {
        properties = new HashMap<String, String>();
    }

    public ExtendableBean(final String name) {
        this.name = name;
        properties = new HashMap<String, String>();
    }

    @JsonAnySetter
    public void add(final String key, final String value) {
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}

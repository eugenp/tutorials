package com.baeldung.jackson.jsonmerge;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonMerge;

public class ObjectWithMap {

    private String name;
    @JsonMerge
    private Map<String, String> stringPairs;

    public ObjectWithMap(String name, Map<String, String> stringPairs) {
        this.name = name;
        this.stringPairs = stringPairs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getStringPairs() {
        return stringPairs;
    }

    public void setStringPairs(Map<String, String> stringPairs) {
        this.stringPairs = stringPairs;
    }

    @Override
    public String toString() {
        return "ObjectWithMap{" + "name='" + name + '\'' + ", something=" + stringPairs + '}';
    }
}

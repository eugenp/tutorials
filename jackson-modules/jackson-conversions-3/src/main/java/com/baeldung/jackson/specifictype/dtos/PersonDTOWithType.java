package com.baeldung.jackson.specifictype.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

public class PersonDTOWithType {
    private List<KeyValuePair> person;

    public PersonDTOWithType() {
    }

    public List<KeyValuePair> getPerson() {
        return person;
    }

    public void setPerson(List<KeyValuePair> person) {
        this.person = person;
    }

    public static class KeyValuePair {
        private String key;

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
        @JsonSubTypes({
                @JsonSubTypes.Type(value = String.class, name = "string"),
                @JsonSubTypes.Type(value = Long.class, name = "long"),
                @JsonSubTypes.Type(value = Integer.class, name = "int")
        })
        private Object value;

        public KeyValuePair() {
        }

        public KeyValuePair(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}

package com.baeldung.jackson.specifictype.dtos;

import com.baeldung.jackson.specifictype.ValueDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class PersonDTOWithCustomDeserializer {
    private List<KeyValuePair> person;

    public PersonDTOWithCustomDeserializer() {
    }

    public List<KeyValuePair> getPerson() {
        return person;
    }

    public void setPerson(List<KeyValuePair> person) {
        this.person = person;
    }

    public static class KeyValuePair {
        private String key;

        @JsonDeserialize(using = ValueDeserializer.class)
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

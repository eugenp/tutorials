package com.baeldung.jackson.specifictype.dtos;

import java.util.List;

public class PersonDTO {
    private List<KeyValuePair> person;

    public PersonDTO() {
    }

    public List<KeyValuePair> getPerson() {
        return person;
    }

    public void setPerson(List<KeyValuePair> person) {
        this.person = person;
    }

    public static class KeyValuePair {
        private String key;

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

package com.baeldung.jsonld.deserialization.jsonldjava.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    @JsonProperty("@id")
    private String id;
    @JsonProperty("http://schema.org/name")
    private String name;
    @JsonProperty("http://schema.org/knows")
    private Link knows;

    public Person() {
    }

    public Person(String id, String name, Link knows) {
        this.id = id;
        this.name = name;
        this.knows = knows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getKnows() {
        return knows;
    }

    public void setKnows(Link knows) {
        this.knows = knows;
    }

    public static class Link {
        @JsonProperty("@id")
        private String id;

        public Link() {
        }

        public Link(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

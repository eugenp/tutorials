package com.baeldung.jsonld.deserialization.jsonldjava.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    @JsonProperty("@id")
    public String id;
    @JsonProperty("http://schema.org/name")
    public String name;
    @JsonProperty("http://schema.org/knows")
    public Link knows;

    public static class Link {
        @JsonProperty("@id")
        public String id;
    }
}

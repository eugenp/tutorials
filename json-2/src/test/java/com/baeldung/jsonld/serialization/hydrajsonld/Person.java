package com.baeldung.jsonld.serialization.hydrajsonld;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.escalon.hypermedia.hydra.mapping.Expose;
import de.escalon.hypermedia.hydra.mapping.Vocab;

@Vocab("http://example.com/vocab/")
@Expose("person")
public class Person {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("@id")
    public String getId() {
        return id;
    }

    @Expose("fullName")
    public String getName() {
        return name;
    }
}

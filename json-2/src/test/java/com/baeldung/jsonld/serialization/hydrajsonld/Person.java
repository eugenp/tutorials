package com.baeldung.jsonld.serialization.hydrajsonld;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.escalon.hypermedia.hydra.mapping.Expose;
import de.escalon.hypermedia.hydra.mapping.Vocab;

@Vocab("http://example.com/vocab/")
@Expose("person")
public class Person {
    @JsonProperty("@id")
    public String id;
    @Expose("fullName")
    public String name;
}

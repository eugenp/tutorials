package com.baeldung.jsonld.serialization.jacksonjsonld;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldNamespace;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldResource
@JsonldNamespace(name = "s", uri = "http://schema.org/")
@JsonldType("s:Person")
@JsonldLink(rel = "s:knows", name = "knows", href = "http://example.com/person/2345")
public class Person {
    @JsonldId
    public String id = "http://example.com/person/1234";
    @JsonldProperty("s:name")
    public String name = "Example Name";
}

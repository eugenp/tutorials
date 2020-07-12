package com.baeldung.jsonld.serialization.jacksonjsonld;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldLink;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldNamespace;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

public class JacksonJsonLdSerializationUnitTest {
    @JsonldResource
    @JsonldNamespace(name = "s", uri = "http://schema.org/")
    @JsonldType("s:Person")
    @JsonldLink(rel = "s:knows", name = "knows", href = "http://example.com/person/2345")
    static class Person {
        @JsonldId
        public String id = "http://example.com/person/1234";
        @JsonldProperty("s:name")
        public String name = "Example Name";
    }

    @Test
    void givenAJacksonJsonldAnnotatedObject_whenJsonldModuleIsUsed_thenAJsonLdDocumentIsGenerated() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule());

        Person person = new Person();
        String personJsonLd = objectMapper.writeValueAsString(person);

        assertEquals(
            "{\"@type\":\"s:Person\",\"@context\":{\"s\":\"http://schema.org/\",\"name\":\"s:name\",\"knows\":{\"@id\":\"s:knows\",\"@type\":\"@id\"}},\"name\":\"Example Name\",\"@id\":\"http://example.com/person/1234\",\"knows\":\"http://example.com/person/2345\"}",
            personJsonLd);
    }
}

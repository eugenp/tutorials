package com.baeldung.jsonld.deserialization.jsonldjava.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.baeldung.jsonld.deserialization.jsonldjava.jackson.Person.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

public class JacksonDeserializationUnitTest {

    @Test
    void givenAJsonLdObject_whenCompactIsUsedWithEmptyContext_thenItCanBeDeserializedIntoAJacksonAnnotatedJavaObject() throws IOException {
        String inputJsonLd = "{\"@context\":{\"@vocab\":\"http://schema.org/\",\"knows\":{\"@type\":\"@id\"}},\"@type\":\"Person\",\"@id\":\"http://example.com/person/1234\",\"name\":\"Example Name\",\"knows\":\"http://example.com/person/2345\"}";

        Object jsonObject = JsonUtils.fromString(inputJsonLd);
        Object compact = JsonLdProcessor.compact(jsonObject, new HashMap(), new JsonLdOptions());
        String compactContent = JsonUtils.toString(compact);

        assertEquals("{\"@id\":\"http://example.com/person/1234\",\"@type\":\"http://schema.org/Person\",\"http://schema.org/knows\":{\"@id\":\"http://example.com/person/2345\"},\"http://schema.org/name\":\"Example Name\"}", compactContent);

        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(compactContent, Person.class);

        Person expectedPerson = new Person();
        expectedPerson.id = "http://example.com/person/1234";
        expectedPerson.name = "Example Name";
        expectedPerson.knows = new Link();
        expectedPerson.knows.id = "http://example.com/person/2345";

        assertEquals(expectedPerson.id, person.id);
        assertEquals(expectedPerson.name, person.name);
        assertEquals(expectedPerson.knows.id, person.knows.id);
    }
}

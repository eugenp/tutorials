package com.baeldung.jackson.deserialization.immutable;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImmutableObjectDeserializationUnitTest {

    @Test
    public void whenPublicConstructorIsUsed_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\",\"age\":50}";
        Person person = new ObjectMapper().readValue(json, Person.class);

        assertEquals("Frank", person.getName());
        assertEquals(50, person.getAge());
    }

    @Test
    public void whenBuilderIsUsedAndFieldIsNull_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\",\"age\":50}";
        MaritalAwarePerson person = new ObjectMapper().readValue(json, MaritalAwarePerson.class);

        assertEquals("Frank", person.getName());
        assertEquals(50, person.getAge());
        assertNull(person.getMarried());
    }

    @Test
    public void whenBuilderIsUsedAndAllFieldsPresent_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\",\"age\":50,\"married\":true}";
        MaritalAwarePerson person = new ObjectMapper().readValue(json, MaritalAwarePerson.class);

        assertEquals("Frank", person.getName());
        assertEquals(50, person.getAge());
        assertTrue(person.getMarried());
    }
}

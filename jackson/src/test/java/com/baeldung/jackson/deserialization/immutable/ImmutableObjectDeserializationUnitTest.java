package com.baeldung.jackson.deserialization.immutable;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImmutableObjectDeserializationUnitTest {

    @Test
    public void whenPublicConstructorIsUsed_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\",\"id\":5000}";
        Employee employee = new ObjectMapper().readValue(json, Employee.class);

        assertEquals("Frank", employee.getName());
        assertEquals(5000, employee.getId());
    }

    @Test
    public void whenBuilderIsUsedAndFieldIsNull_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\"}";
        Person person = new ObjectMapper().readValue(json, Person.class);

        assertEquals("Frank", person.getName());
        assertNull(person.getAge());
    }

    @Test
    public void whenBuilderIsUsedAndAllFieldsPresent_thenObjectIsDeserialized() throws IOException {
        final String json = "{\"name\":\"Frank\",\"age\":50}";
        Person person = new ObjectMapper().readValue(json, Person.class);

        assertEquals("Frank", person.getName());
        assertEquals(50, (int) person.getAge());
    }
}

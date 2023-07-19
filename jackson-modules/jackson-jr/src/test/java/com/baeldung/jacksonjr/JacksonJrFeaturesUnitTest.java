package com.baeldung.jacksonjr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class JacksonJrFeaturesUnitTest {

    @Test
    public void whenSerializingObject_thenReturnJson() throws IOException {
        String json = JacksonJrFeatures.jsonObject();
        assertTrue(json.contains("name"));
        assertTrue(json.contains("age"));
    }

    @Test
    public void whenSerializingComposer_thenReturnJson() throws IOException {
        String json = JacksonJrFeatures.jsonComposer();
        assertTrue(json.contains("objectArray"));
        assertTrue(json.contains("object"));
    }

    @Test
    public void whenSerializingSimpleObject_thenAnnotationIsNotConsidered() throws IOException {
        Person person = new Person("John Doe", 30, null);
        String json = JacksonJrFeatures.objectSerialization(person);
        assertTrue(json.contains("name"));
        assertFalse(json.contains("person_name"));
    }

    @Test
    public void whenDeserializingJsonObject_thenObjectsAreEqual() throws IOException {
        Person person = new Person("John Doe", 30, null);
        String json = JacksonJrFeatures.objectSerialization(person);
        Person deserializedPerson = JacksonJrFeatures.objectDeserialization(json);
        assertEquals(person, deserializedPerson);
    }

    @Test
    public void whenSerializingWithAnnotations_thenAnnotationIsConsidered() throws IOException {
        Person person = new Person("John Doe", 30, null);
        String json = JacksonJrFeatures.objectAnnotationSerialization(person);
        assertTrue(json.contains("person_name"));
    }

    @Test
    public void whenSerializingCustomObject_thenLocalDateIsSerializedAsString() throws IOException {
        Person person = new Person("John Doe", 30, LocalDate.now());
        String json = JacksonJrFeatures.customObjectSerialization(person);
        System.out.println(json);
        assertTrue(json.contains("birthDate"));
    }

    @Test
    public void whenDeserializingCustomObject_thenLocalDateIsDeserialized() throws IOException {
        Person person = new Person("John Doe", 30, LocalDate.now());
        String json = JacksonJrFeatures.customObjectSerialization(person);
        Person deserializedPerson = JacksonJrFeatures.customObjectDeserialization(json);
        assertEquals(person, deserializedPerson);
    }
}

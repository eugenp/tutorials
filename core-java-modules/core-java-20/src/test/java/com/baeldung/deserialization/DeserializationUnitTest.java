package com.baeldung.deserialization;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeserializationUnitTest {

    @Test
    public void givenJsonString_whenDeserialized_thenPersonRecordCreated() {
        String json = "{\"name\":\"John Doe\",\"age\":30,\"address\":\"123 Main St\"}";

        Person person = new Gson().fromJson(json, Person.class);

        assertEquals("John Doe", person.name());
        assertEquals(30, person.age());
        assertEquals("123 Main St", person.address());
    }

    @Test
    public void givenNestedJsonString_whenDeserialized_thenPersonRecordCreated() {
        String json = "{\"name\":\"John Doe\",\"age\":30,\"address\":\"123 Main St\",\"contact\":{\"email\":\"john.doe@example.com\",\"phone\":\"555-1234\"}}";

        Person person = new Gson().fromJson(json, Person.class);

        assertNotNull(person);
        assertEquals("John Doe", person.name());
        assertEquals(30, person.age());
        assertEquals("123 Main St", person.address());

        Contact contact = person.contact();

        assertNotNull(contact);
        assertEquals("john.doe@example.com", contact.email());
        assertEquals("555-1234", contact.phone());
    }
}

package com.baeldung.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.baeldung.gson.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUnitTest {

    @Test
    public void testSerialization() {
        User user = new User("John Doe", 30, "john.doe@example.com");
        user.setId(12345L);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
          .create();
        String json = gson.toJson(user);

        // Verify that name, age, and id are serialized, but email is not
        assertEquals("{\"firstName\":\"John Doe\",\"age\":30,\"id\":12345}", json);
    }

    @Test
    public void testDeserialization() {
        String jsonInput = "{\"firstName\":\"Jane Doe\",\"age\":25,\"id\":67890,\"email\":\"jane.doe@example.com\"}";

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
          .create();
        User user = gson.fromJson(jsonInput, User.class);

        // Verify that name and age are deserialized, but email and id are not
        assertEquals("Jane Doe", user.getName());
        assertEquals(25, user.getAge());
        assertEquals(0, user.getId());
        assertNull(user.getEmail());
    }

    @Test
    public void testDeserializationWithAlternateNames() {
        String jsonInput1 = "{\"firstName\":\"Jane Doe\",\"age\":25,\"id\":67890,\"email\":\"jane.doe@example.com\"}";
        String jsonInput2 = "{\"fullName\":\"John Doe\",\"age\":30,\"id\":12345,\"email\":\"john.doe@example.com\"}";
        String jsonInput3 = "{\"name\":\"Alice\",\"age\":28,\"id\":54321,\"email\":\"alice@example.com\"}";

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
          .create();

        User user1 = gson.fromJson(jsonInput1, User.class);
        User user2 = gson.fromJson(jsonInput2, User.class);
        User user3 = gson.fromJson(jsonInput3, User.class);

        // Verify that the name field is correctly deserialized from different JSON field names
        assertEquals("Jane Doe", user1.getName());
        assertEquals("John Doe", user2.getName());
        assertEquals("Alice", user3.getName());
    }
}

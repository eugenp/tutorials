package com.baeldung.pojomapping;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class JsonToPojoMapperUnitTest {

    private JSONObject simpleJsonObject;
    private JSONObject jsonObjectWithPhones;
    private String jsonArrayString;

    @BeforeEach
    public void setUp() {
        String simpleJsonString = """
    {
      "name": "Alice",
      "age": 25,
      "email": "alice@example.com",
      "address": {
        "city": "Singapore",
        "postalCode": "123456"
      }
    }
    """;
        simpleJsonObject = new JSONObject(simpleJsonString);

        String jsonStringWithPhones = """
    {
      "name": "Alice",
      "age": 25,
      "email": "alice@example.com",
      "address": {
        "city": "Singapore",
        "postalCode": "123456"
      },
      "phones": ["12345678", "87654321"]
    }
    """;
        jsonObjectWithPhones = new JSONObject(jsonStringWithPhones);

        jsonArrayString = """
    [
      { "type": "dog", "name": "Buddy", "barkVolume": 5 },
      { "type": "cat", "name": "Mimi", "likesFish": true }
    ]
    """;
    }

    @Test
    public void givenSimpleJsonObject_whenMappedManually_thenUserPojoIsCreatedCorrectly() {
        User user = JsonToPojoMapper.mapManually(simpleJsonObject);

        assertEquals("Alice", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("Singapore", user.getAddress().getCity());
        assertEquals("123456", user.getAddress().getPostalCode());
    }

    @Test
    public void givenNestedJsonObject_whenMappedManually_thenAddressIsPopulatedCorrectly() {
        User user = JsonToPojoMapper.mapManually(simpleJsonObject);

        assertNotNull(user.getAddress());
        assertEquals("Singapore", user.getAddress().getCity());
        assertEquals("123456", user.getAddress().getPostalCode());
    }

    @Test
    public void givenSimpleJsonObject_whenMappedWithJackson_thenUserPojoIsCreatedCorrectly() {
        User user = JsonToPojoMapper.mapWithJackson(simpleJsonObject);

        assertEquals("Alice", user.getName());
        assertEquals("Singapore", user.getAddress().getCity());
        assertEquals("123456", user.getAddress().getPostalCode());
    }

    @Test
    public void givenJsonObjectWithPhones_whenMappedWithJackson_thenPhonesListIsPopulatedCorrectly() {
        User user = JsonToPojoMapper.mapWithJackson(jsonObjectWithPhones);

        assertEquals("Alice", user.getName());
        assertEquals("Singapore", user.getAddress().getCity());
        assertEquals("123456", user.getAddress().getPostalCode());
        assertEquals(2, user.getPhones().size());
        assertEquals("12345678", user.getPhones().get(0));
        assertEquals("87654321", user.getPhones().get(1));
    }

    @Test
    public void givenSimpleJsonObject_whenMappedWithGson_thenUserPojoIsCreatedCorrectly() {
        User user = JsonToPojoMapper.mapWithGson(simpleJsonObject);

        assertEquals("Alice", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("Singapore", user.getAddress().getCity());
    }

    @Test
    public void givenJsonObjectWithPhones_whenMappedWithGson_thenPhonesListIsPopulatedCorrectly() {
        User user = JsonToPojoMapper.mapWithGson(jsonObjectWithPhones);

        assertEquals("Alice", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("Singapore", user.getAddress().getCity());
        assertEquals(2, user.getPhones().size());
    }

    @Test
    public void givenPolymorphicJsonArray_whenMappedWithJackson_thenCorrectSubclassesAreCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Animal> animals = Arrays.asList(
            mapper.readValue(jsonArrayString, Animal[].class)
        );

        assertEquals(2, animals.size());

        assertTrue(animals.get(0) instanceof Dog);
        Dog dog = (Dog) animals.get(0);
        assertEquals("Buddy", dog.getName());
        assertEquals(5, dog.getBarkVolume());

        assertTrue(animals.get(1) instanceof Cat);
        Cat cat = (Cat) animals.get(1);
        assertEquals("Mimi", cat.getName());
        assertTrue(cat.isLikesFish());
    }
}
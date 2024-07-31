package com.baeldung.jackson.map;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapWithJsonKeyValueUnitTest {
    private static final Fruit FRUIT1 = new Fruit("Alphonso", "Mango");
    private static final Fruit FRUIT2 = new Fruit("Black", "Grapes");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void givenObject_WhenSerialize_ThenUseJsonValueForSerialization() throws JsonProcessingException {
        String serializedValueForFruit1 = OBJECT_MAPPER.writeValueAsString(FRUIT1);
        Assertions.assertEquals("\"Alphonso Mango\"", serializedValueForFruit1);
        String serializedValueForFruit2 = OBJECT_MAPPER.writeValueAsString(FRUIT2);
        Assertions.assertEquals("\"Black Grapes\"", serializedValueForFruit2);
    }

    @Test
    public void givenMapWithObjectKeys_WhenSerialize_ThenUseJsonKeyForSerialization() throws JsonProcessingException {
        // Given
        Map<Fruit, String> selectionByFruit = new LinkedHashMap();
        selectionByFruit.put(FRUIT1, "Hagrid");
        selectionByFruit.put(FRUIT2, "Hercules");
        // When
        String serializedValue = OBJECT_MAPPER.writeValueAsString(selectionByFruit);
        // Then
        Assertions.assertEquals("{\"Mango\":\"Hagrid\",\"Grapes\":\"Hercules\"}", serializedValue);
    }

    @Test
    public void givenMapWithObjectValues_WhenSerialize_ThenUseJsonValueForSerialization() throws JsonProcessingException {
        // Given
        Map<String, Fruit> selectionByPerson = new LinkedHashMap();
        selectionByPerson.put("Hagrid", FRUIT1);
        selectionByPerson.put("Hercules", FRUIT2);
        // When
        String serializedValue = OBJECT_MAPPER.writeValueAsString(selectionByPerson);
        // Then
        Assertions.assertEquals("{\"Hagrid\":\"Alphonso Mango\",\"Hercules\":\"Black Grapes\"}", serializedValue);
    }
}

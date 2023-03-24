package com.baeldung.lombok.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

class LombokWithJacksonFruitUnitTest {

    @Test
    void withFruitObject_thenSerializeSucessfully() throws IOException {
        Fruit fruit = Fruit.builder()
            .id(101)
            .name("Apple")
            .build();

        String json = newObjectMapper().writeValueAsString(fruit);
        assertEquals("{\"name\":\"Apple\",\"id\":101}", json);
    }

    @Test
    public void withFruitJSON_thenDeserializeSucessfully() throws IOException {
        String json = "{\"name\":\"Apple\",\"id\":101}";
        Fruit fruit = newObjectMapper().readValue(json, Fruit.class);
        assertEquals(new Fruit("Apple", 101), fruit);
    }

    private ObjectMapper newObjectMapper() {
        return new ObjectMapper();
    }

}

package com.baeldung.spring.enums.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.baeldung.spring.model.Modes;
import com.fasterxml.jackson.databind.ObjectMapper;

class ModeDeserializationUnitTest {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenJsonContainsA_thenDeserializesToAlpha() throws Exception {
        ModeEntity entity = objectMapper.readValue("{\"mode\": \"A\"}", ModeEntity.class);

        assertEquals(Modes.ALPHA, entity.getMode());
    }
}
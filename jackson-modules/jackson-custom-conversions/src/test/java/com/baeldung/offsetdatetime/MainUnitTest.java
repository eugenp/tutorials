package com.baeldung.offsetdatetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainUnitTest {

    @Test
    void givenUser_whenSerialized_thenCreatedDateIsSerialized() throws JsonProcessingException {
        Assertions.assertEquals("{\"createdAt\":\"2021-09-30T15:30:00+01:00\"}", Main.serializeUser());
    }

    @Test
    void givenUser_whenCustomSerialized_thenCreatedDateIsSerialized() throws JsonProcessingException {
        Assertions.assertEquals("{\"createdAt\":\"30-09-2021 15:30:00 +01:00\"}", Main.customSerialize());
    }

    @Test
    void givenUser_whenCustomDeserialized_thenCreatedDateIsDeserialized() throws JsonProcessingException {
        Assertions.assertEquals("2021-09-30T15:30+01:00", Main.customDeserialize());
    }
}
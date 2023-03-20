package com.baeldung.offsetdatetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainUnitTest {

    @Test
    void serializeUser() throws JsonProcessingException {
        Assertions.assertEquals("{\"createdAt\":\"2021-09-30T15:30:00+01:00\"}", Main.serializeUser());
    }

    @Test
    void customSerialize() throws JsonProcessingException {
        Assertions.assertEquals("{\"createdAt\":\"30-09-2021 15:30:00 +01:00\"}", Main.customSerialize());
    }

    @Test
    void customDeserialize() throws JsonProcessingException {
        Assertions.assertEquals("2021-09-30T15:30+01:00", Main.customDeserialize());
    }
}
package com.baeldung.jackson.staticobjectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HiddenCouplingUnitTest {

    private static final ObjectMapper GLOBAL_MAPPER = new ObjectMapper();

    @Test
    @Order(1)
    void givenCustomDateFormat_whenConfiguredFirst_thenPasses() throws Exception {
        GLOBAL_MAPPER.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        Map<String, Date> payload = Collections.singletonMap("date", Date.from(LocalDate.of(1998, 2, 9).atTime(12, 0).toInstant(ZoneOffset.UTC)));
        String json = GLOBAL_MAPPER.writeValueAsString(payload);
        assertEquals("{\"date\":\"09-02-1998\"}", json);
    }

    @Test 
    @Order(2)
    void givenDefaultDateFormat_whenRunAfterMutation_thenFails() throws Exception {
        Map<String, Date> payload = Collections.singletonMap("date", Date.from(LocalDate.of(1998, 2, 9).atTime(12, 0).toInstant(ZoneOffset.UTC)));
        String json = GLOBAL_MAPPER.writeValueAsString(payload);
        assertNotEquals("{\"date\":887025600000}", json);
    }
}
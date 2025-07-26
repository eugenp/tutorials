package com.baeldung.jackson.staticobjectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConflictingRequirementsUnitTest {

    private static final ObjectMapper GLOBAL_MAPPER = new ObjectMapper();

    @Test
    void whenSwitchingDateFormatGlobally_thenEndpointsCollide() throws Exception {
        SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
        GLOBAL_MAPPER.setDateFormat(iso);

        Map<String, Date> payload = Collections.singletonMap(
                "dob",
                Date.from(LocalDate.of(1990, 10, 5)
                        .atTime(12, 0)
                        .toInstant(ZoneOffset.UTC)));

        String forA = GLOBAL_MAPPER.writeValueAsString(payload);
        assertEquals("{\"dob\":\"1990-10-05\"}", forA);

        SimpleDateFormat european = new SimpleDateFormat("dd/MM/yyyy");
        GLOBAL_MAPPER.setDateFormat(european);

        String forB = GLOBAL_MAPPER.writeValueAsString(payload);
        assertEquals("{\"dob\":\"05/10/1990\"}", forB);

        String nowBrokenForA = GLOBAL_MAPPER.writeValueAsString(payload);
        assertNotEquals(forA, nowBrokenForA);
    }

}
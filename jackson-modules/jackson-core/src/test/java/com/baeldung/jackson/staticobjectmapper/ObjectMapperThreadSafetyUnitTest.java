package com.baeldung.jackson.staticobjectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ObjectMapperThreadSafetyUnitTest {

    private ObjectMapper GLOBAL_MAPPER = new ObjectMapper();

    /**
     * two real threads, created once and reused for every repetition
     */
    private static final ExecutorService POOL = Executors.newFixedThreadPool(2);

    @Test
    void whenRegisteringDateFormatGlobally_thenAffectsAllConsumers() throws Exception {
        Map<String, Date> payload = singletonMap("today", Date.from(LocalDate.of(1998, 2, 9).atTime(12, 0).toInstant(ZoneOffset.UTC)));

        String before = GLOBAL_MAPPER.writeValueAsString(payload);
        assertEquals("{\"today\":887025600000}", before);

        GLOBAL_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        String after = GLOBAL_MAPPER.writeValueAsString(payload);
        assertEquals("{\"today\":\"1998-02-09\"}", after);
    }
    
    @Test
    void whenSimpleDateFormatChanges_thenConflictHappens() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        GLOBAL_MAPPER.setDateFormat(format);

        Callable<String> task = () -> GLOBAL_MAPPER.writeValueAsString(singletonMap("key", Date.from(LocalDate.of(1998, 2, 9).atTime(12, 0).toInstant(ZoneOffset.UTC))));
        Callable<Void> mutator = () -> {
            format.applyPattern("dd-MM-yyyy");
            return null;
        };

        Future<String> taskResult1 = POOL.submit(task);
        assertEquals("{\"key\":\"1998-02-09\"}", taskResult1.get());
        POOL.submit(mutator).get();
        Future<String> taskResult2 = POOL.submit(task);
        assertEquals("{\"key\":\"09-02-1998\"}", taskResult2.get());
    }

    @Test
    void whenUsingCopyScopedMapper_thenNoInterference() throws Exception {
        ObjectMapper localCopy = GLOBAL_MAPPER.copy().enable(SerializationFeature.INDENT_OUTPUT);
        assertEquals("{\n  \"key\" : \"value\"\n}", localCopy.writeValueAsString(singletonMap("key", "value")));
        assertEquals("{\"key\":\"value\"}", GLOBAL_MAPPER.writeValueAsString(singletonMap("key", "value")));
    }

    @BeforeEach
    void setup() {
        GLOBAL_MAPPER = new ObjectMapper();
    }
    
    @AfterAll
    static void shutdownPool() {
        POOL.shutdownNow();
    }
}

package com.baeldung.jackson.staticobjectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterAll;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


class ObjectMapperThreadSafetyTest {

    private static final ObjectMapper GLOBAL_MAPPER = new ObjectMapper();

    /**
     * two real threads, created once and reused for every repetition
     */
    private static final ExecutorService POOL = Executors.newFixedThreadPool(2);

    @Test
    void whenSimpleDateFormatChanges_thenConflictHappens() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        GLOBAL_MAPPER.setDateFormat(format);

        Callable<String> task = () -> GLOBAL_MAPPER.writeValueAsString(Map.of("key", Date.from(LocalDate.of(1998, 2, 9).atTime(12, 0).toInstant(ZoneOffset.UTC))));
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
        assertEquals("{\n  \"key\" : \"value\"\n}", localCopy.writeValueAsString(Map.of("key", "value")));
        assertEquals("{\"key\":\"value\"}", GLOBAL_MAPPER.writeValueAsString(Map.of("key", "value")));
    }

    @AfterAll
    static void shutdownPool() {
        POOL.shutdownNow();
    }
}

package com.baeldung.paralleltesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("parallel")
@Tag("singleton")
public class TestSingleton4 {

    private long start;
    private static long startAll;

    @BeforeAll
    static void beforeAll() {
        startAll = Instant.now()
            .toEpochMilli();
    }

    @AfterAll
    static void afterAll() {
        long endAll = Instant.now()
            .toEpochMilli();
        System.out.println("Total time: " + (endAll - startAll) + " ms");
    }

    @BeforeEach
    void setUp() {
        start = Instant.now()
            .toEpochMilli();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        Integer worker = Integer.valueOf(System.getProperty("org.gradle.test.worker", "1"));
        String jvmName = ManagementFactory.getRuntimeMXBean()
            .getName();
        long end = Instant.now()
            .toEpochMilli();
        System.out.println("Class " + getClass().getSimpleName() + " with worker " + worker + " on " + jvmName + " started at " + localTimeFromMilli(start) +
            " ended at " + localTimeFromMilli(end) + ": (" + (end - start) + " ms)");
    }

    @Test
    public void whenOneRequest_thenSuccess() throws InterruptedException {
        ClassSingleton testSingleton = ClassSingleton.getInstance();
        assertEquals(1, testSingleton.getCount());
        Thread.sleep(1000L);
    }

    private LocalTime localTimeFromMilli(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalTime();
    }
}
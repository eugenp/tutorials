package com.baeldung.paralleltesting;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
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
@Tag("UnitTest")
public class UnitTestClass3 {

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
        long end = Instant.now()
            .toEpochMilli();
        String name = testInfo.getDisplayName();
        System.out.println("Test " + name + " from class " + getClass().getSimpleName() + " started at " + localTimeFromMilli(start) + " ended at " +
            localTimeFromMilli(end) + ": (" + (end - start) + " ms)");
    }

    private LocalTime localTimeFromMilli(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalTime();
    }

    @Test
    public void whenAny_thenCorrect1() throws InterruptedException {
        Thread.sleep(1000L);
        assertTrue(true);
    }

    @Test
    public void whenAny_thenCorrect2() throws InterruptedException {
        Thread.sleep(1000L);
        assertTrue(true);
    }

    @Test
    public void whenAny_thenCorrect3() throws InterruptedException {
        Thread.sleep(1000L);
        assertTrue(true);
    }

    @Test
    public void whenAny_thenCorrect4() throws InterruptedException {
        Thread.sleep(1000L);
        assertTrue(true);
    }
}
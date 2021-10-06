package com.baeldung.maps;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoordinateKeyUnitTest {

    private Map<CoordinateKey, Color> pixels = new HashMap<>();

    @Test
    void testOptimalKey() {
        // setup
        CoordinateKey coord = new CoordinateKey(1, 2);
        pixels.put(coord, Color.CYAN);
        // read out color correctly
        assertEquals(Color.CYAN, pixels.get(coord));
    }

    @Test
    void testSlowKey() {
        // setup
        CoordinateKey coord = new CoordinateSlowKey(1, 2);
        pixels.put(coord, Color.CYAN);
        // read out color correctly
        assertEquals(Color.CYAN, pixels.get(coord));
    }

    // Performance Test Parameters - change here
    private static final int MAX_X = 100;
    private static final int MAX_Y = 100;
    private static final int COUNT_OF_QUERIES = 1000;
    private static final int QUERY_X = 1;
    private static final int QUERY_Y = 1;

    @Tag("performance")
    @Test
    void testKeyPerformance() {
        // generate some sample keys and values
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                pixels.put(new CoordinateKey(x, y), new Color(x % 255, y % 255, (x + y) % 255));
            }
        }
        // read out multiple times and measure time
        CoordinateKey coord = new CoordinateKey(QUERY_X, QUERY_Y);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < COUNT_OF_QUERIES; i++) {
            assertNotNull(pixels.get(coord));
        }
        long t2 = System.currentTimeMillis();
        System.out.printf("Optimal key performance: %d ms%n", t2 - t1);
    }

    @Tag("performance")
    @Test
    void testSlowKeyPerformance() {
        // generate some sample keys and values
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                pixels.put(new CoordinateSlowKey(x, y), new Color(x % 255, y % 255, (x + y) % 255));
            }
        }
        // read out multiple times and measure time
        CoordinateKey coord = new CoordinateSlowKey(QUERY_X, QUERY_Y);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < COUNT_OF_QUERIES; i++) {
            assertNotNull(pixels.get(coord));
        }
        long t2 = System.currentTimeMillis();
        System.out.printf("Slow key performance: %d ms%n", t2 - t1);
    }

}

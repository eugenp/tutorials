package com.baeldung.maps;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CoordinateMutableKeyUnitTest {

    @Test
    void testKeyMutable() {
        // setup
        Map<CoordinateMutableKey, Color> pixels = new HashMap<>();
        CoordinateMutableKey coord = new CoordinateMutableKey(1, 2);
        pixels.put(coord, Color.CYAN);
        // read out color correctly
        assertEquals(Color.CYAN, pixels.get(coord));
        // change key's hashcode should result in null value
        coord.setX(10);
        assertNull(pixels.get(coord));
    }

}

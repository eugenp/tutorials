package com.baeldung.loops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.baeldung.initializationguide.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopsUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoopsUnitTest.class);
    private LoopsInJava loops = new LoopsInJava();
    private static List<String> list = new ArrayList<>();
    private static Set<String> set = new HashSet<>();
    private static Map<String, Integer> map = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        list.add("One");
        list.add("Two");
        list.add("Three");

        set.add("Four");
        set.add("Five");
        set.add("Six");

        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
    }

    @Test
    public void shouldRunForLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.simple_for_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunEnhancedForeachLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.enhanced_for_each_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunWhileLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.while_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunDoWhileLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.do_while_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void whenUsingSimpleFor_shouldIterateList() {
        for (int i = 0; i < list.size(); i++) {
            LOGGER.debug(list.get(i));
        }
    }

    @Test
    public void whenUsingEnhancedFor_shouldIterateList() {
        for (String item : list) {
            LOGGER.debug(item);
        }
    }

    @Test
    public void whenUsingEnhancedFor_shouldIterateSet() {
        for (String item : set) {
            LOGGER.debug(item);
        }
    }

    @Test
    public void whenUsingEnhancedFor_shouldIterateMap() {
        for (Entry<String, Integer> entry : map.entrySet()) {
            LOGGER.debug("Key: " + entry.getKey() + " - " + "Value: " + entry.getValue());
        }
    }

    @Test
    public void whenUsingSimpleFor_shouldRunLabelledLoop() {
        aa:
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                continue;
            }
            bb:
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    break aa;
                }
                LOGGER.debug(i + " " + j);
            }
        }
    }

}

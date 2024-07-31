package com.baeldung.mapandhashmap.printer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapPrinterUnitTest {

    private final MapPrinter mapPrinter = new MapPrinter();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Test printer with TreeMap")
    void testPrintTreeMap() {
        // given
        String key = "TreeMap";
        String value = "Used when sorting is needed";
        String expected = key + " " + value;
        TreeMap<String, String> map = new TreeMap<>();
        map.put(key, value);
        // when
        mapPrinter.printMap(map);
        // then
        String actual = outputStreamCaptor.toString().trim();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test printer with HashMap")
    void testPrintHashMap() {
        // given
        String key = "HashMap";
        String value = "Main default implementation for the Map interface";
        String expected = key + " " + value;
        HashMap<String, String> map = new HashMap<>();
        map.put(key, value);
        // when
        mapPrinter.printMap(map);
        // then
        String actual = outputStreamCaptor.toString().trim();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test printer with LinkedHash")
    void testPrintLinkedHashMap() {
        // given
        String key = "LinkedHashMap";
        String value = "Use this implementation if you need keep the order of elements";
        String expected = key + " " + value;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(key, value);
        // when
        mapPrinter.printMap(map);
        // then
        String actual = outputStreamCaptor.toString().trim();
        assertThat(actual).isEqualTo(expected);
    }
}
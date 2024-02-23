package com.baeldung.map.prettyprintmap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.MapUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

class PrettyPrintMapUnitTest {

    private static final Map<String, Object> MAP;

    static {
        // using LinkedHashMap to keep insertion order, helpful in assertions
        MAP = new LinkedHashMap<>();
        MAP.put("one", 1);
        MAP.put("two", 2);

        Map<String, Integer> innerMap = new LinkedHashMap<>();
        innerMap.put("ten", 10);
        innerMap.put("eleven", 11);

        MAP.put("inner", innerMap);
    }

    @Test
    void givenMap_whenToString_thenOneLine() {
        String result = MAP.toString();

        String expected = "{one=1, two=2, inner={ten=10, eleven=11}}";
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenMap_whenSimpleForEachLoop_thenPrettyPrintWithoutNested() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<?, ?> entry : MAP.entrySet()) {
            result.append(String.format("%-15s : %s%n", entry.getKey(), entry.getValue()));
        }

        String expected =
            "one             : 1\n" +
            "two             : 2\n" +
            "inner           : {ten=10, eleven=11}\n";
        Assertions.assertThat(result.toString()).isEqualToIgnoringNewLines(expected);
    }

    @Test
    void givenMap_whenRecursionForEachLoop_thenPrettyPrint() {
        String result = printMap(0, MAP);

        String expected =
            "one             : 1\n" +
            "two             : 2\n" +
            "inner           :\n" +
            "    ten             : 10\n" +
            "    eleven          : 11\n";
        Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
    }

    @Test
    void givenMap_whenStream_thenPrettyPrintWithoutNested() {
        StringBuilder result = new StringBuilder();

        MAP.forEach((k, v) -> result.append(String.format("%-15s : %s%n", k, v)));

        String expected =
            "one             : 1\n" +
            "two             : 2\n" +
            "inner           : {ten=10, eleven=11}\n";
        Assertions.assertThat(result.toString()).isEqualToIgnoringNewLines(expected);
    }

    @Test
    void givenMap_whenExtendedStream_thenPrettyPrintWithoutNested() {
        String result = MAP.entrySet().stream()
            .map(entry -> String.format("%-15s : %s", entry.getKey(), entry.getValue()))
            .collect(Collectors.joining("\n"));

        String expected =
            "one             : 1\n" +
            "two             : 2\n" +
            "inner           : {ten=10, eleven=11}\n";
        Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
    }
    @Test
    void givenMap_whenJackson_thenPrettyPrint() throws JsonProcessingException {
        String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(MAP);

        String expected =
            "{\n" +
            "  \"one\" : 1,\n" +
            "  \"two\" : 2,\n" +
            "  \"inner\" : {\n" +
            "    \"ten\" : 10,\n" +
            "    \"eleven\" : 11\n" +
            "  }\n" +
            "}";
        Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
    }

    @Test
    void givenMap_whenGson_thenPrettyPrint() {
        String result = new GsonBuilder().setPrettyPrinting().create().toJson(MAP);

        String expected =
            "{\n" +
            "  \"one\": 1,\n" +
            "  \"two\": 2,\n" +
            "  \"inner\": {\n" +
            "    \"ten\": 10,\n" +
            "    \"eleven\": 11\n" +
            "  }\n" +
            "}";
        Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
    }

    @Test
    void givenMap_whenApacheCommonsCollectionsDebugPrint_thenPrettyPrintWithClassNames() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream ps = new PrintStream(baos)) {

            MapUtils.debugPrint(ps, "map", MAP);
            String result = baos.toString();

            String expected =
                "map = \n" +
                "{\n" +
                "    one = 1 java.lang.Integer\n" +
                "    two = 2 java.lang.Integer\n" +
                "    inner = \n" +
                "    {\n" +
                "        ten = 10 java.lang.Integer\n" +
                "        eleven = 11 java.lang.Integer\n" +
                "    } java.util.LinkedHashMap\n" +
                "} java.util.LinkedHashMap\n";
            Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
        }
    }

    @Test
    void givenMap_whenApacheCommonsCollectionsVerbosePrint_thenPrettyPrint() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream ps = new PrintStream(baos)) {

            MapUtils.verbosePrint(ps, "map", MAP);
            String result = baos.toString();

            String expected =
                "map = \n" +
                "{\n" +
                "    one = 1\n" +
                "    two = 2\n" +
                "    inner = \n" +
                "    {\n" +
                "        ten = 10\n" +
                "        eleven = 11\n" +
                "    }\n" +
                "}\n";
            Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
        }
    }

    @Test
    void givenMap_whenGuavaJoiner_thenPrettyPrintWithoutNested() {
        String result = Joiner.on(",\n").withKeyValueSeparator("=").join(MAP);

        String expected =
            "one=1,\n" +
            "two=2,\n" +
            "inner={ten=10, eleven=11}";
        Assertions.assertThat(result).isEqualToIgnoringNewLines(expected);
    }

    private static String printMap(int leftPadding, Map<?, ?> map) {
        StringBuilder ret = new StringBuilder();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                ret.append(String.format("%-15s :%n", entry.getKey()));
                ret.append(printMap(leftPadding + 4, (Map<?, ?>) entry.getValue()));
            }
            else {
                ret.append(String.format("%" + (leftPadding > 0 ? leftPadding : "") + "s" // adding padding
                        + "%-15s : %s%n",
                    "", entry.getKey(), entry.getValue()));
            }
        }
        return ret.toString();
    }

}

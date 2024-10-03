package com.baeldung.jsonprettyprinter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonPrettyPrinterUnitTest {

    private String uglyJsonString = "{\"one\":\"AAA\",\"two\":[\"BBB\",\"CCC\"],\"three\":{\"four\":\"DDD\",\"five\":[\"EEE\",\"FFF\"]}}";
    private JsonPrettyPrinter jsonPrettyPrinter = new JsonPrettyPrinter();

    @Test
    public void shouldPrettyPrintJsonStringUsingDefaultPrettyPrinter() throws JsonProcessingException {
        String formattedJsonString = jsonPrettyPrinter.prettyPrintJsonUsingDefaultPrettyPrinter(uglyJsonString);
        String expectedJson = "{" + System.lineSeparator() +
            "  \"one\" : \"AAA\"," + System.lineSeparator() +
            "  \"two\" : [ \"BBB\", \"CCC\" ]," + System.lineSeparator() +
            "  \"three\" : {" + System.lineSeparator() +
            "    \"four\" : \"DDD\"," + System.lineSeparator() +
            "    \"five\" : [ \"EEE\", \"FFF\" ]" + System.lineSeparator() +
            "  }" + System.lineSeparator() +
            "}";
        System.out.println("Formatted String: " + formattedJsonString);
        assertEquals(expectedJson, formattedJsonString);
    }

    @Test
    public void shouldPrettyPrintJsonStringUsingGlobalSetting() throws JsonProcessingException {
        String formattedJsonString = jsonPrettyPrinter.prettyPrintUsingGlobalSetting(uglyJsonString);
        String expectedJson = "{" + System.lineSeparator() +
            "  \"one\" : \"AAA\"," + System.lineSeparator() +
            "  \"two\" : [ \"BBB\", \"CCC\" ]," + System.lineSeparator() +
            "  \"three\" : {" + System.lineSeparator() +
            "    \"four\" : \"DDD\"," + System.lineSeparator() +
            "    \"five\" : [ \"EEE\", \"FFF\" ]" + System.lineSeparator() +
            "  }" + System.lineSeparator() +
            "}";
        System.out.println("Formatted String: " + formattedJsonString);
        assertEquals(expectedJson, formattedJsonString);
    }

    @Test
    public void shouldPrettyPrintJsonStringUsingGson() {
        String formattedJsonString = jsonPrettyPrinter.prettyPrintUsingGson(uglyJsonString);
        String expectedPrettyJson = "{\n" +
            "  \"one\": \"AAA\",\n" +
            "  \"two\": [\n" +
            "    \"BBB\",\n" +
            "    \"CCC\"\n" +
            "  ],\n" +
            "  \"three\": {\n" +
            "    \"four\": \"DDD\",\n" +
            "    \"five\": [\n" +
            "      \"EEE\",\n" +
            "      \"FFF\"\n" +
            "    ]\n" +
            "  }\n" +
            "}";
        System.out.println("Formatted String: " + formattedJsonString);
        assertEquals(expectedPrettyJson, formattedJsonString);
    }
}

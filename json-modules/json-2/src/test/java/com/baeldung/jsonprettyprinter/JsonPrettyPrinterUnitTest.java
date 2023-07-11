package core;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonPrettyPrinterUnitTest {

    private String uglyJsonString = "{\"one\":\"AAA\",\"two\":[\"BBB\",\"CCC\"],\"three\":{\"four\":\"DDD\",\"five\":[\"EEE\",\"FFF\"]}}";
    private core.JsonPrettyPrinter jsonPrettyPrinter = new core.JsonPrettyPrinter();

    @Test
    public void shouldPrettyPrintJsonStringUsingDefaultPrettyPrinter() throws JsonProcessingException {
        String formattedJsonString = jsonPrettyPrinter.prettyPrintJsonUsingDefaultPrettyPrinter(uglyJsonString);
        String expectedJson = "{\n" +
            "  \"one\" : \"AAA\",\n" +
            "  \"two\" : [ \"BBB\", \"CCC\" ],\n" +
            "  \"three\" : {\n" +
            "    \"four\" : \"DDD\",\n" +
            "    \"five\" : [ \"EEE\", \"FFF\" ]\n" +
            "  }\n" +
            "}";
        assertEquals(expectedJson, formattedJsonString);
    }

    @Test
    public void shouldPrettyPrintJsonStringUsingGlobalSetting() throws JsonProcessingException {
        String formattedJsonString = jsonPrettyPrinter.prettyPrintUsingGlobalSetting(uglyJsonString);
        String expectedJson = "{\n" +
            "  \"one\" : \"AAA\",\n" +
            "  \"two\" : [ \"BBB\", \"CCC\" ],\n" +
            "  \"three\" : {\n" +
            "    \"four\" : \"DDD\",\n" +
            "    \"five\" : [ \"EEE\", \"FFF\" ]\n" +
            "  }\n" +
            "}";
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
        assertEquals(expectedPrettyJson, formattedJsonString);
    }
}

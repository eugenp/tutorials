package com.baeldung.jsonpath.introduction;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class JsonPathUnitTest {

    private static String json;
    private static File jsonFile = new File("src/main/resources/online_store.json");

    private static String readFile(File file, Charset charset) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), charset);
    }

    @BeforeClass
    public static void init() throws IOException {
        json = readFile(jsonFile, StandardCharsets.UTF_8);
    }

    @Test
    public void shouldMatchCountOfObjects() {
        Map<String, String> objectMap = JsonPath.read(json, "$");
        assertEquals(3, objectMap.keySet()
            .size());
    }

    @Test
    public void shouldMatchCountOfArrays() {
        JSONArray jsonArray = JsonPath.read(json, "$.items.book[*]");
        assertEquals(2, jsonArray.size());
    }

}

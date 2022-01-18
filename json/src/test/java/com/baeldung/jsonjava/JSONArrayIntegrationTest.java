package com.baeldung.jsonjava;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JSONArrayIntegrationTest {

    @Test
    public void givenJSONJava_thenCreateNewJSONArrayFromScratch() {
        JSONArray ja = new JSONArray();
        ja.put(Boolean.TRUE);
        ja.put("lorem ipsum");

        // We can also put a JSONObject in JSONArray
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");

        ja.put(jo);

        assertThatJson(ja)
          .isEqualTo("[true,\"lorem ipsum\",{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}]");
    }

    @Test
    public void givenJsonString_thenCreateNewJSONArray() {
        JSONArray ja = new JSONArray("[true, \"lorem ipsum\", 215]");

        assertThatJson(ja)
          .isEqualTo("[true,\"lorem ipsum\",215]");
    }

    @Test
    public void givenListObject_thenConvertItToJSONArray() {
        List<String> list = new ArrayList<>();
        list.add("California");
        list.add("Texas");
        list.add("Hawaii");
        list.add("Alaska");

        JSONArray ja = new JSONArray(list);

        assertThatJson(ja)
          .isEqualTo("[\"California\",\"Texas\",\"Hawaii\",\"Alaska\"]");
    }
}
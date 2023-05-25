package com.baeldung.jsonjava;

import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JSONObjectIntegrationTest {

    @Test
    public void givenJSONJava_thenCreateNewJSONObject() {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }

    @Test
    public void givenMapObject_thenCreateJSONObject() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");
        JSONObject jo = new JSONObject(map);

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }

    @Test
    public void givenJsonString_thenCreateJSONObject() {
        JSONObject jo = new JSONObject(
          "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}"
        );

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }
}

package com.baeldung.jsonjava;

import org.json.JSONObject;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JSONObjectIntegrationTest {

    @Test
    public void givenJSONJava_whenCreatingNewJSONObject_thenCorrectNewJSONObjectCreated() {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }

    @Test
    void givenJSON_whenParsed_thenCorrectValueReturned() {
        String jsonString = """
                            {
                                "type": "Feature", 
                                "geometry": "Point",                          
                                "properties": {
                                                  "isValid": true, 
                                                  "name": "Sample Point"
                                              }
                            }
                            """;
        JSONObject jsonObject = new JSONObject(jsonString);
        String type = jsonObject.getString("type");
        String geometry = jsonObject.getString("geometry");
        JSONObject properties = jsonObject.getJSONObject("properties");
        boolean isValid = properties.getBoolean("isValid");
        assertEquals("Feature",type);
        assertEquals("Point",geometry);
        assertTrue(isValid);
    }

    @Test
    public void givenMapObject_whenCreatingNewJSONObject_thenCorrectNewJSONObjectCreated() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");
        JSONObject jo = new JSONObject(map);

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }

    @Test
    public void givenJSONString_whenCreatingNewJSONObject_thenCorrectNewJSONObjectCreated() {
        JSONObject jo = new JSONObject(
          "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}"
        );

        assertThatJson(jo)
          .isEqualTo("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}");
    }
}

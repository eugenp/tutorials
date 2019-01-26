package com.baeldung.jsonjava;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

public class JSONObjectIntegrationTest {
    @Test
    public void givenJSONJava_thenCreateNewJSONObject() {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");
             
        assertEquals("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}", jo.toString());
        
    }

    @Test
    public void givenMapObject_thenCreateJSONObject() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");
        JSONObject jo = new JSONObject(map);
         
        assertEquals("{\"name\":\"jon doe\",\"city\":\"chicago\",\"age\":\"22\"}", jo.toString());
    }

    @Test
    public void givenJsonString_thenCreateJSONObject() {
        JSONObject jo = new JSONObject(
          "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}"
        );
        
        assertEquals("{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}", jo.toString());
    }

    @Test
    public void givenDemoBean_thenCreateJSONObject() {
        DemoBean demo = new DemoBean();
        demo.setId(1);
        demo.setName("lorem ipsum");
        demo.setActive(true);
         
        JSONObject jo = new JSONObject(demo);
        assertEquals("{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}", jo.toString());
    }
}

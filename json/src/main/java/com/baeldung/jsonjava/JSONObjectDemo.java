package com.baeldung.jsonjava;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class JSONObjectDemo {
    public static void main(String[] args) {
        System.out.println("4.1. Creating JSONObject: ");
        jsonFromJSONObject();
        
        System.out.println("\n4.2. Creating JSONObject from Map: ");
        jsonFromMap();
        
        System.out.println("\n4.3. Creating JSONObject from JSON string: ");
        jsonFromJSONString();
    }
    
    public static void jsonFromJSONObject() {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");
             
        System.out.println(jo.toString());
    }
    
    public static void jsonFromMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "jon doe");
        map.put("age", "22");
        map.put("city", "chicago");
        JSONObject jo = new JSONObject(map);
         
        System.out.println(jo.toString());
    }
    
    public static void jsonFromJSONString() {
        JSONObject jo = new JSONObject(
          "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}"
        );
        
        System.out.println(jo.toString());
    }
}

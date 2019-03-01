package com.baeldung.jsonjava;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArrayDemo {
    public static void main(String[] args) {
        System.out.println("6.1. Creating JSON Array: ");
        creatingJSONArray();
        
        System.out.println("\n6.2. Creating JSON Array from JSON string: ");
        jsonArrayFromJSONString();
        
        System.out.println("\n6.3. Creating JSON Array from Collection Object: ");
        jsonArrayFromCollectionObj();
    }
    
    public static void creatingJSONArray() {
        JSONArray ja = new JSONArray();
        ja.put(Boolean.TRUE);
        ja.put("lorem ipsum");
         
        // We can also put a JSONObject in JSONArray
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");
         
        ja.put(jo);
         
        System.out.println(ja.toString());
    }
    
    public static void jsonArrayFromJSONString() {
        JSONArray ja = new JSONArray("[true, \"lorem ipsum\", 215]");
        System.out.println(ja);
    }
    
    public static void jsonArrayFromCollectionObj() {
        List<String> list = new ArrayList<>();
        list.add("California");
        list.add("Texas");
        list.add("Hawaii");
        list.add("Alaska");
         
        JSONArray ja = new JSONArray(list);
        System.out.println(ja);
    }
}
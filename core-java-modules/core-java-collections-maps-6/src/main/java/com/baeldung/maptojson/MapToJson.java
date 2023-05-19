package com.baeldung.maptojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapToJson {
    public static String convertMapToJsonUsingJackson() {
        Map<String, String> data = new HashMap();
        data.put("CS", "Post1");
        data.put("Linux", "Post1");
        data.put("Kotlin", "Post1");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String convertMapToJsonUsingGson() {
        Map<String, String> data = new HashMap<>();
        data.put("CS", "Post1");
        data.put("Linux", "Post1");
        data.put("Kotlin", "Post1");
        Gson gson = new Gson();
        Type typeObject = new TypeToken<HashMap>() {
        }.getType();
        try {
            return gson.toJson(data, typeObject);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertMapToJsonUsingOrgJson() {
        Map<String, String> data = new HashMap<>();
        data.put("CS", "Post1");
        data.put("Linux", "Post1");
        data.put("Kotlin", "Post1");
        try {
            JSONObject jsonObject = new JSONObject(data);
            return jsonObject.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String jacksonData = convertMapToJsonUsingJackson();
        String gsonData = convertMapToJsonUsingGson();
        String orgJsonData = convertMapToJsonUsingOrgJson();
        System.out.println(jacksonData);
        System.out.println(gsonData);
        System.out.println(orgJsonData);
    }
}
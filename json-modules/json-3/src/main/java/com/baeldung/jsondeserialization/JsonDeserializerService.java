package com.baeldung.jsondeserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

public class JsonDeserializerService {
    private final ObjectMapper objectMapper;
    private final Gson gson;

    public JsonDeserializerService() {
        this.objectMapper = new ObjectMapper();
        this.gson = new Gson();
    }

    public Map<String, Object> deserializeUsingJackson(String jsonString) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            return objectMapper.readValue(jsonString, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("Jackson deserialization failed: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> deserializeUsingGson(String jsonString) {
        try {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            return gson.fromJson(jsonString, type);
        } catch (Exception e) {
            throw new RuntimeException("Gson deserialization failed: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> deserializeUsingOrgJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, Object> result = new HashMap<>();

            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if (value instanceof JSONArray) {
                    value = ((JSONArray) value).toList();
                } else if (value instanceof JSONObject) {
                    value = ((JSONObject) value).toMap();
                }
                result.put(key, value);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("org.json deserialization failed: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> deserializeUsingJsonP(String jsonString) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = reader.readObject();
            return convertJsonToMap(jsonObject);
        } catch (Exception e) {
            throw new RuntimeException("JSON-P deserialization failed: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> convertJsonToMap(JsonObject jsonObject) {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonValue value = entry.getValue();
            result.put(key, convertJsonValue(value));
        }

        return result;
    }

    private Object convertJsonValue(JsonValue jsonValue) {
        switch (jsonValue.getValueType()) {
            case STRING:
                return ((JsonString) jsonValue).getString();
            case NUMBER:
                JsonNumber num = (JsonNumber) jsonValue;
                return num.isIntegral() ? num.longValue() : num.doubleValue();
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            case ARRAY:
                return convertJsonArray(( jakarta.json.JsonArray) jsonValue);
            case OBJECT:
                return convertJsonToMap((JsonObject) jsonValue);
            default:
                return jsonValue.toString();
        }
    }

    private List<Object> convertJsonArray( jakarta.json.JsonArray jsonArray) {
        List<Object> list = new ArrayList<>();
        for (JsonValue value : jsonArray) {
            list.add(convertJsonValue(value));
        }
        return list;
    }
}

package com.baeldung.gson.jsonarraytohashmap;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class JSONArrayToHashMapConverter {
    public static Map<String, Object> convertUsingIterative (JsonArray jsonArray) {
        Map<String, Object> hashMap = new HashMap<>();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    hashMap.put(entry.getKey(), entry.getValue().getAsJsonPrimitive().getAsString());
                } else {
                    hashMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return hashMap;
    }

    public static Map<String, Object> convertUsingStreams (JsonArray jsonArray) {
        return StreamSupport.stream(jsonArray.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .flatMap(jsonObject -> jsonObject.entrySet().stream())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().isJsonPrimitive() ? entry.getValue().getAsJsonPrimitive().getAsString() : entry.getValue()
            ));
    }

    public static Map<String, Object> convertUsingGson(JsonArray jsonArray) {
        Map<String, Object> hashMap = new HashMap<>();
        Gson gson = new Gson();
        List<Map<String, Object>> list = new Gson().fromJson(jsonArray, List.class);
        for (Map<String, Object> entry : list) {
            hashMap.putAll(entry);
        }
        return hashMap;
    }

    public static void main (String args []) {
        JsonArray jsonArray = new JsonArray();

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("name", "John Doe");
        jsonObject1.addProperty("age", "35");
        jsonArray.add(jsonObject1);

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("job", "Programmer");
        jsonObject2.addProperty("department", "IT");
        jsonArray.add(jsonObject2);
        Map<String, Object> hashMap = convertUsingIterative(jsonArray);

        System.out.println(hashMap);
    }
}

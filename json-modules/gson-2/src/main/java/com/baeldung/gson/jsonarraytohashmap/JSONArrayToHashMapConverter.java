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
    public static Map<String, Integer> convertUsingIterative (JsonArray jsonArray) {
        Map<String, Integer> hashMap = new HashMap<>();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            String type = jsonObject.get("name").getAsString();
            Integer amount = jsonObject.get("age").getAsInt();
            hashMap.put(type, amount);
        }
        return hashMap;
    }

    public static Map<String, Integer> convertUsingStreams (JsonArray jsonArray) {
        return StreamSupport.stream(jsonArray.spliterator(), false)
            .map(JsonElement::getAsJsonObject)
            .collect(Collectors.toMap(
                jsonObject -> jsonObject.get("name").getAsString(),
                jsonObject -> jsonObject.get("age").getAsInt()
            ));
    }

    public static Map<String, Integer> convertUsingGson(JsonArray jsonArray) {
        Map<String, Integer> hashMap = new HashMap<>();
        Gson gson = new Gson();
        List<Map<String, Object>> list = new Gson().fromJson(jsonArray, List.class);
        for (Map<String, Object> map : list) {
            String type = (String) map.get("name");
            Integer amount = ((Double) map.get("age")).intValue(); // Gson parses numbers as Double
            hashMap.put(type, amount);
        }
        return hashMap;
    }
}

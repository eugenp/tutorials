package com.baeldung.gson.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JsonKeyExtractor {

    public static List<String> getAllKeys(JsonObject jsonObject) {
        List<String> keys = new ArrayList<>();
        extractKeys("", jsonObject, keys);
        return keys;
    }

    private static void extractKeys(String prefix, JsonObject jsonObject, List<String> keys) {
        Set<String> jsonKeys = jsonObject.keySet();

        for (String key : jsonKeys) {
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;
            keys.add(fullKey);

            JsonElement element = jsonObject.get(key);

            if (element.isJsonObject()) {
                extractKeys(fullKey, element.getAsJsonObject(), keys);
            }
        }
    }
}
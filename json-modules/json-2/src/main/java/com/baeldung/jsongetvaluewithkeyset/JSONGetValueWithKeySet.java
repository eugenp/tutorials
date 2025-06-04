package com.baeldung.jsongetvaluewithkeyset;

import org.json.JSONObject;
import java.util.Set;
import java.util.HashSet;

public class JSONGetValueWithKeySet {
    public static Set<String> extractKeys(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.keySet();
    }

    public static void extractNestedKeys(JSONObject jsonObject, String parentKey, Set<String> result) {
        for (String key : jsonObject.keySet()) {
            String fullKey = parentKey.isEmpty() ? key : parentKey + "." + key;
            Object value = jsonObject.get(key);
            result.add(fullKey);
            if (value instanceof JSONObject) {
                extractNestedKeys((JSONObject) value, fullKey, result);
            }
        }
    }
}

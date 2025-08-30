package com.baeldung.jsonobjecttojsonarray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

public class GsonConverter {

    JsonArray convertToKeyValueArray(JSONObject jsonObject) {
        JsonArray result = new JsonArray();
        jsonObject.keySet().forEach(key -> {
            JsonObject entry = new JsonObject();
            entry.addProperty("key", key);
            entry.add("value", com.google.gson.JsonParser.parseString(jsonObject.get(key).toString()));
            result.add(entry);
        });
        return result;
    }
}

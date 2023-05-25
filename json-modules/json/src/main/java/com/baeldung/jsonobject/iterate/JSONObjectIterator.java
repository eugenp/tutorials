package com.baeldung.jsonobject.iterate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectIterator {

    private Map<String, Object> keyValuePairs;

    public JSONObjectIterator() {
        keyValuePairs = new HashMap<>();
    }

    public void handleValue(String key, Object value) {
        if (value instanceof JSONArray) {
            handleJSONArray(key, (JSONArray) value);
        } else if (value instanceof JSONObject) {
            handleJSONObject((JSONObject) value);
        }
        keyValuePairs.put(key, value);
    }

    public void handleJSONObject(JSONObject jsonObject) {
        Iterator<String> jsonObjectIterator = jsonObject.keys();
        jsonObjectIterator.forEachRemaining(key -> {
            Object value = jsonObject.get(key);
            handleValue(key, value);
        });
    }

    public void handleJSONArray(String key, JSONArray jsonArray) {
        Iterator<Object> jsonArrayIterator = jsonArray.iterator();
        jsonArrayIterator.forEachRemaining(element -> {
            handleValue(key, element);
        });
    }

    public Map<String, Object> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(Map<String, Object> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }

}

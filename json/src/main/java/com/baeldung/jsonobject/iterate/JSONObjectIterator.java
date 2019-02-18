package com.baeldung.jsonobject.iterate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectIterator {

    private Map<String, Object> keyValuePairs;

    public JSONObjectIterator() {
        keyValuePairs = new HashMap<>();
    }

    public void handleJSONObject(JSONObject jsonObject) {
        Iterator<String> jsonObjectIterator = jsonObject.keys();
        jsonObjectIterator.forEachRemaining(key -> {
            Object value = jsonObject.get(key);
            boolean isJSONArray = value instanceof JSONArray;
            boolean isJSONObject = value instanceof JSONObject;
            if (isJSONArray) {
                handleJSONArray(key, jsonObject.getJSONArray(key));
            } else if (isJSONObject) {
                handleJSONObject(jsonObject.getJSONObject(key));
            }
            keyValuePairs.put(key, value);
        });
    }

    public void handleJSONArray(String key, JSONArray jsonArray) {
        Iterator<Object> jsonArrayIterator = jsonArray.iterator();
        List<Object> list = new ArrayList<>();
        jsonArrayIterator.forEachRemaining(element -> {
            if (element instanceof JSONObject) {
                handleJSONObject((JSONObject) element);
            } else if (element instanceof JSONArray) {
                handleJSONArray(key, (JSONArray) element);
            } else {
                list.add(element);
            }
        });
        keyValuePairs.put(key, list);
    }

    public Map<String, Object> getKeyValuePairs() {
        return keyValuePairs;
    }

    public void setKeyValuePairs(Map<String, Object> keyValuePairs) {
        this.keyValuePairs = keyValuePairs;
    }

}

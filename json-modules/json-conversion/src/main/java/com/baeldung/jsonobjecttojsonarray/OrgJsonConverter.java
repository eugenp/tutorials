package com.baeldung.jsonobjecttojsonarray;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrgJsonConverter {

    JSONArray convertValuesToArray(JSONObject jsonObject) {
        return new JSONArray(jsonObject.toMap().values());
    }

    JSONArray convertToEntryArray(JSONObject jsonObject) {
        JSONArray result = new JSONArray();
        for (String key : jsonObject.keySet()) {
            JSONObject entry = new JSONObject();
            entry.put("key", key);
            entry.put("value", jsonObject.get(key));
            result.put(entry);
        }
        return result;
    }
}
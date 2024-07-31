package com.baeldung.jsonjava;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONArrayGetValueByKey {

    public List<String> getValuesByKeyInJSONArray(String jsonArrayStr, String key) {
        List<String> values = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        for (int idx = 0; idx < jsonArray.length(); idx++) {
            JSONObject jsonObj = jsonArray.getJSONObject(idx);
            values.add(jsonObj.optString(key));
        }
        return values;
    }

    public List<String> getValuesByKeyInJSONArrayUsingJava8(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
            .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key))
            .collect(Collectors.toList());
    }

}

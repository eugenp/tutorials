package com.baeldung.jsonvaluegetter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONObjectValueGetter {

    /**
     * Get values associated with the provided key in the given JSONObject instance
     *
     * @param jsonObject JSONObject instance in which to search the key
     * @param key        Key we're interested in
     *
     * @return List of values associated with the given key, in the order of appearance.
     *         If the key is absent, empty list is returned.
     */
    public List<String> getValuesInObject(JSONObject jsonObject, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (String currentKey : jsonObject.keySet()) {
            Object value = jsonObject.get(currentKey);
            if (currentKey.equals(key)) {
                accumulatedValues.add(value.toString());
            }

            if (value instanceof JSONObject) {
                accumulatedValues.addAll(getValuesInObject((JSONObject)value, key));
            } else if (value instanceof JSONArray) {
                accumulatedValues.addAll(getValuesInArray((JSONArray)value, key));
            }
        }

        return accumulatedValues;
    }

    /**
     * Get values associated with the provided key in the given JSONArray instance
     *
     * @param jsonArray JSONArray instance in which to search the key
     * @param key       Key we're interested in
     *
     * @return List of values associated with the given key, in the order of appearance.
     *         If the key is absent, empty list is returned.
     */
    public List<String> getValuesInArray(JSONArray jsonArray, String key) {
        List<String> accumulatedValues = new ArrayList<>();
        for (Object obj : jsonArray) {
            if (obj instanceof JSONArray) {
                accumulatedValues.addAll(getValuesInArray((JSONArray)obj, key));
            } else if (obj instanceof JSONObject) {
                accumulatedValues.addAll(getValuesInObject((JSONObject)obj, key));
            }
        }

        return accumulatedValues;
    }

    /**
     * Among all the values associated with the given key, get the N-th value
     *
     * @param jsonObject JSONObject instance in which to search the key
     * @param key        Key we're interested in
     * @param N          Index of the value to get
     *
     * @return N-th value associated with the key, or null if the key is absent or
     *         the number of values associated with the key is less than N
     */
    public String getNthValue(JSONObject jsonObject, String key, int N) {
        List<String> values = getValuesInObject(jsonObject, key);
        return (values.size() >= N) ? values.get(N - 1) : null;
    }

    /**
     * Count the number of values associated with the given key
     *
     * @param jsonObject JSONObject instance in which to count the key
     * @param key        Key we're interested in
     *
     * @return The number of values associated with the given key
     */
    public int getCount(JSONObject jsonObject, String key) {
        List<String> values = getValuesInObject(jsonObject, key);
        return values.size();
    }
}

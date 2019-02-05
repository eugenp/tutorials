package com.baeldung.jsonobject.iterate;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectIterateDemo {

    public static void main(String[] args) {
        JSONObjectIterateDemo jsonObjectIterate = new JSONObjectIterateDemo();
        JSONObject jsonObject = jsonObjectIterate.getJSONObject();
        jsonObjectIterate.handleJSONObject(jsonObject);
    }

    public void handleJSONObject(JSONObject jsonObject) {
        Iterator<String> jsonObjectIterator = jsonObject.keys();
        jsonObjectIterator.forEachRemaining(key -> {
            Object value = jsonObject.get(key);
            System.out.println("Key: " + key);
            if (value instanceof JSONArray) {
                handleJSONArray(jsonObject.getJSONArray(key));
            } else if (value instanceof JSONObject) {
                handleJSONObject(jsonObject.getJSONObject(key));
            } else {
                System.out.println("Value: " + jsonObject.get(key));
            }
        });
    }

    public void handleJSONArray(JSONArray jsonArray) {
        Iterator<Object> jsonArrayIterator = jsonArray.iterator();
        jsonArrayIterator.forEachRemaining(element -> {
            if (element instanceof JSONObject)
                handleJSONObject((JSONObject) element);
            else
                System.out.println("Value: " + element.toString());
        });
    }

    public JSONObject getJSONObject() {
        String jsonString = "{  \"id\": \"0001\",  \"type\": \"donut\",  \"name\": \"Cake\",  \"ppu\": 0.55,  \"batters\": { \"batter\":  [ { \"id\": \"1001\", \"type\": \"Regular\" }, { \"id\": \"1002\", \"type\": \"Chocolate\" }, { \"id\": \"1003\", \"type\": \"Blueberry\" }, { \"id\": \"1004\", \"type\": \"Devil's Food\" }  ] },  \"topping\": [ { \"id\": \"5001\", \"type\": \"None\" }, { \"id\": \"5002\", \"type\": \"Glazed\" }, { \"id\": \"5005\", \"type\": \"Sugar\" }, { \"id\": \"5007\", \"type\": \"Powdered Sugar\" }, { \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" }, { \"id\": \"5003\", \"type\": \"Chocolate\" }, { \"id\": \"5004\", \"type\": \"Maple\" } ] }";
        return new JSONObject(jsonString);
    }

}

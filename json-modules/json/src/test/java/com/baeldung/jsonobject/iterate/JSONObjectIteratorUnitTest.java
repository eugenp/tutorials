package com.baeldung.jsonobject.iterate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class JSONObjectIteratorUnitTest {

    private JSONObjectIterator jsonObjectIterator = new JSONObjectIterator();

    @Test
    public void givenJSONObject_whenIterating_thenGetKeyValuePairs() {
        JSONObject jsonObject = getJsonObject();

        jsonObjectIterator.handleJSONObject(jsonObject);

        Map<String, Object> keyValuePairs = jsonObjectIterator.getKeyValuePairs();
        assertThat(keyValuePairs.get("rType")).isEqualTo("Regular");
        assertThat(keyValuePairs.get("rId")).isEqualTo("1001");
        assertThat(keyValuePairs.get("cType")).isEqualTo("Chocolate");
        assertThat(keyValuePairs.get("cId")).isEqualTo("1002");
        assertThat(keyValuePairs.get("bType")).isEqualTo("BlueBerry");
        assertThat(keyValuePairs.get("bId")).isEqualTo("1003");
        assertThat(keyValuePairs.get("name")).isEqualTo("Cake");
        assertThat(keyValuePairs.get("cakeId")).isEqualTo("0001");
        assertThat(keyValuePairs.get("type")).isEqualTo("donut");
        assertThat(keyValuePairs.get("Type")).isEqualTo("Maple");
        assertThat(keyValuePairs.get("tId")).isEqualTo("5001");
        assertThat(keyValuePairs.get("batters")
            .toString()).isEqualTo("[{\"rType\":\"Regular\",\"rId\":\"1001\"},{\"cType\":\"Chocolate\",\"cId\":\"1002\"},{\"bType\":\"BlueBerry\",\"bId\":\"1003\"}]");
        assertThat(keyValuePairs.get("cakeShapes")
            .toString()).isEqualTo("[\"square\",\"circle\",\"heart\"]");
        assertThat(keyValuePairs.get("topping")
            .toString()).isEqualTo("{\"Type\":\"Maple\",\"tId\":\"5001\"}");
    }

    private JSONObject getJsonObject() {
        JSONObject cake = new JSONObject();
        cake.put("cakeId", "0001");
        cake.put("type", "donut");
        cake.put("name", "Cake");

        JSONArray batters = new JSONArray();
        JSONObject regular = new JSONObject();
        regular.put("rId", "1001");
        regular.put("rType", "Regular");
        batters.put(regular);
        JSONObject chocolate = new JSONObject();
        chocolate.put("cId", "1002");
        chocolate.put("cType", "Chocolate");
        batters.put(chocolate);
        JSONObject blueberry = new JSONObject();
        blueberry.put("bId", "1003");
        blueberry.put("bType", "BlueBerry");
        batters.put(blueberry);

        JSONArray cakeShapes = new JSONArray();
        cakeShapes.put("square");
        cakeShapes.put("circle");
        cakeShapes.put("heart");

        cake.put("cakeShapes", cakeShapes);

        cake.put("batters", batters);

        JSONObject topping = new JSONObject();
        topping.put("tId", "5001");
        topping.put("Type", "Maple");

        cake.put("topping", topping);

        return cake;
    }

}

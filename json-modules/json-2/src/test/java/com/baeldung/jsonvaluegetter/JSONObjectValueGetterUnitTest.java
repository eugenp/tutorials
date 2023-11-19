package com.baeldung.jsonvaluegetter;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JSONObjectValueGetterUnitTest {

    private static JSONObject jsonObject;
    private static JSONObjectValueGetter jsonObjectValueGetter = new JSONObjectValueGetter();

    @BeforeAll
    public static void loadJsonContent() throws IOException {
        InputStream inputStream = JSONObjectValueGetterUnitTest.class.getClassLoader().getResourceAsStream("employee.json");
        String jsonString = IOUtils.toString(inputStream, "UTF-8");
        jsonObject = new JSONObject(jsonString);
    }

    @Test
    public void getValueDirectly() {
        JSONArray family = jsonObject.getJSONArray("family");
        JSONObject sonObject = family.getJSONObject(1);
        JSONObject sonData = sonObject.getJSONObject("son");
        String sonName = sonData.getString("name");
        Assertions.assertEquals(sonName, "Peter");
    }

    @Test
    public void getAllAssociatedValuesRecursively() {
        List<String> values = jsonObjectValueGetter.getValuesInObject(jsonObject, "son");
        Assertions.assertEquals(values.size(), 1);

        String sonString = values.get(0);
        Assertions.assertTrue(sonString.contains("Peter"));
        Assertions.assertTrue(sonString.contains("Schoolboy"));
        Assertions.assertTrue(sonString.contains("11"));

        values = jsonObjectValueGetter.getValuesInObject(jsonObject, "name");
        Assertions.assertEquals(values.size(), 3);

        Assertions.assertEquals(values.get(0), "Bob");
        Assertions.assertEquals(values.get(1), "Alice");
        Assertions.assertEquals(values.get(2), "Peter");
    }

    @Test
    public void getNthValueRecursively() {
        Assertions.assertEquals(jsonObjectValueGetter.getNthValue(jsonObject, "name", 1), "Bob");
        Assertions.assertEquals(jsonObjectValueGetter.getNthValue(jsonObject, "name", 2), "Alice");
        Assertions.assertEquals(jsonObjectValueGetter.getNthValue(jsonObject, "name", 3), "Peter");
        Assertions.assertNull(jsonObjectValueGetter.getNthValue(jsonObject, "nonExistingKey", 1));
    }

    @Test
    public void getCountRecursively() {
        Assertions.assertEquals(jsonObjectValueGetter.getCount(jsonObject, "name"), 3);
        Assertions.assertEquals(jsonObjectValueGetter.getCount(jsonObject, "age"), 3);
        Assertions.assertEquals(jsonObjectValueGetter.getCount(jsonObject, "occupation"), 1);
        Assertions.assertEquals(jsonObjectValueGetter.getCount(jsonObject, "nonExistingKey"), 0);
    }
}

package org.baeldung.gson.primitives.charvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonChar;

public class JsonUnicodeCharValue {
    public static void main(String[] args) {
        // The field is converted.
        Gson gson = new Gson();
        String json = "{\"value\": \"\\u00AE\"}";
        GsonChar model = gson.fromJson(json, GsonChar.class);

        System.out.println(model);
    }
}

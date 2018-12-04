package org.baeldung.gson.primitives.numbervalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonNonCompatibleNumberTypeValue {
    public static void main(String[] args) {
        // Raises an exception.
        Gson gson = new Gson();
        String json = "{\"value\": 2.3}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

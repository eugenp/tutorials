package org.baeldung.gson.primitives.specialvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonValidStringValue {
    public static void main(String[] args) {
        // The field is converted.
        Gson gson = new Gson();
        String json = "{\"value\": \"15\"}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

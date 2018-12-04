package org.baeldung.gson.primitives.specialvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonInvalidStringValue {
    public static void main(String[] args) {
        // Raises an exception.
        Gson gson = new Gson();
        String json = "{\"value\": \"15x\"}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

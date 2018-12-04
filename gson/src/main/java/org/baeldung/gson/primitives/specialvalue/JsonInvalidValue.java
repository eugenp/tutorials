package org.baeldung.gson.primitives.specialvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonInvalidValue {
    public static void main(String[] args) {
        // Raises an exception.
        Gson gson = new Gson();
        String json = "{\"value\": s15s}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

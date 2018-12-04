package org.baeldung.gson.primitives.numbervalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonOverflowValue {
    public static void main(String[] args) {
        // Overflow happens unnoticed.
        Gson gson = new Gson();
        String json = "{\"value\": \"300\"}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

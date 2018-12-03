package com.baeldung.gson_primitive_types.number_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.Gson;

public class JsonOverflowValue {
    public static void main(String[] args) {
        // Overflow happens unnoticed.
        Gson gson = new Gson();
        String json = "{\"value\": \"300\"}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

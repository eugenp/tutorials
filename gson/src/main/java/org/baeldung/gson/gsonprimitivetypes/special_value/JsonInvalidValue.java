package com.baeldung.gson_primitive_types.special_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.Gson;

public class JsonInvalidValue {
    public static void main(String[] args) {
        // Raises an exception.
        Gson gson = new Gson();
        String json = "{\"value\": s15s}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

package com.baeldung.gson_primitive_types.special_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.Gson;

public class JsonValidStringValue {
    public static void main(String[] args) {
        // The field is converted.
        Gson gson = new Gson();
        String json = "{\"value\": \"15\"}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

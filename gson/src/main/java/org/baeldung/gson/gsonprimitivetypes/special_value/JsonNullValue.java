package com.baeldung.gson_primitive_types.special_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.Gson;

public class JsonNullValue {
    public static void main(String[] args) {
        // The field will just be ignored.
        Gson gson = new Gson();
        String json = "{\"value\": null}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

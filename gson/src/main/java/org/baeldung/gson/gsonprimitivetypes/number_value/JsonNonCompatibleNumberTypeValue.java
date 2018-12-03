package com.baeldung.gson_primitive_types.number_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.Gson;

public class JsonNonCompatibleNumberTypeValue {
    public static void main(String[] args) {
        // Raises an exception.
        Gson gson = new Gson();
        String json = "{\"value\": 2.3}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

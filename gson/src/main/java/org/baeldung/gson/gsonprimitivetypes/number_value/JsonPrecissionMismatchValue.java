package com.baeldung.gson_primitive_types.number_value;

import com.baeldung.gson_primitive_types.models.GsonFloat;
import com.google.gson.Gson;

public class JsonPrecissionMismatchValue {
    public static void main(String[] args) {
        String json = "{\"value\": 12.123456789123456}";
        Gson gson = new Gson();

        GsonFloat model = gson.fromJson(json, GsonFloat.class);

        System.out.println(model);
    }
}

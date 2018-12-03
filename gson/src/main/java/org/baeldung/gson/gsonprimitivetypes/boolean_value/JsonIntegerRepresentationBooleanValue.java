package com.baeldung.gson_primitive_types.boolean_value;

import com.baeldung.gson_primitive_types.models.GsonBoolean;
import com.baeldung.gson_primitive_types.models.GsonFloat;
import com.google.gson.Gson;

public class JsonIntegerRepresentationBooleanValue {
    public static void main(String[] args) {
        // Raises exception.
        String json = "{\"value\": 1}";
        Gson gson = new Gson();

        GsonBoolean model = gson.fromJson(json, GsonBoolean.class);

        System.out.println(model);
    }
}

package com.baeldung.gson_primitive_types.boolean_value;

import com.baeldung.gson_primitive_types.models.GsonBoolean;
import com.google.gson.Gson;

public class JsonInvalidValueWithinStringBooleanValue {
    public static void main(String[] args) {
        // It is ignored.
        String json = "{\"value\": \"15x\"}";
        Gson gson = new Gson();

        GsonBoolean model = gson.fromJson(json, GsonBoolean.class);

        System.out.println(model);
    }
}

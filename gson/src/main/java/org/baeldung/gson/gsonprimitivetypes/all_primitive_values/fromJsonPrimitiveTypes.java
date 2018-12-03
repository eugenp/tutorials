package com.baeldung.gson_primitive_types.all_primitive_values;

import com.baeldung.gson_primitive_types.models.GsonBundle;
import com.google.gson.Gson;

public class fromJsonPrimitiveTypes {
    public static void main(String[] args) {
        String json = "{\"value\": 17, \"shortValue\": 3, \"intValue\": 3, "
            + "\"longValue\": 3, \"floatValue\": 3.5" + ", \"doubleValue\": 3.5"
            + ", \"booleanValue\": true, \"charValue\": \"a\"}";

        Gson gson = new Gson();
        GsonBundle gsonBundle = gson.fromJson(json, GsonBundle.class);

        System.out.println(gsonBundle);
    }
}

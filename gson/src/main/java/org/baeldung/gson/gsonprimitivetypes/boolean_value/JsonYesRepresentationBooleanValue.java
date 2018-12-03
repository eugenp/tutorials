package com.baeldung.gson_primitive_types.boolean_value;

import com.baeldung.gson_primitive_types.models.GsonBoolean;
import com.google.gson.Gson;

public class JsonYesRepresentationBooleanValue {
    public static void main(String[] args) {
        // It fails silently.
        String json = "{\"value\": yes}";
        Gson gson = new Gson();

        GsonBoolean model = gson.fromJson(json, GsonBoolean.class);

        System.out.println(model);
    }
}

package org.baeldung.gson.primitives.booleanvalue;

import com.google.gson.Gson;

public class JsonInvalidValueWithinStringBooleanValue {
    public static void main(String[] args) {
        // It is ignored.
        String json = "{\"value\": \"15x\"}";
        Gson gson = new Gson();

        org.baeldung.gson.primitives.models.GsonBoolean model = gson.fromJson(json, org.baeldung.gson.primitives.models.GsonBoolean.class);

        System.out.println(model);
    }
}

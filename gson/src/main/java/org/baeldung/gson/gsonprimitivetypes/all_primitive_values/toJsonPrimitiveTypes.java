package com.baeldung.gson_primitive_types.all_primitive_values;

import com.baeldung.gson_primitive_types.models.GsonBundle;
import com.google.gson.Gson;

public class toJsonPrimitiveTypes {
    public static void main(String[] args) {
        GsonBundle gsonBundle = new GsonBundle();
        Gson gson = new Gson();

        System.out.println(gson.toJson(gsonBundle));
    }
}

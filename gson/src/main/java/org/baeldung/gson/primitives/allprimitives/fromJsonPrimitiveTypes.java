package org.baeldung.gson.primitives.allprimitives;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBundle;

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

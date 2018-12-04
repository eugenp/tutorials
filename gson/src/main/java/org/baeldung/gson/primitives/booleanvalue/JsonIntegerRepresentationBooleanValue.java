package org.baeldung.gson.primitives.booleanvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBoolean;

public class JsonIntegerRepresentationBooleanValue {
    public static void main(String[] args) {
        // Raises exception.
        String json = "{\"value\": 1}";
        Gson gson = new Gson();

        GsonBoolean model = gson.fromJson(json, GsonBoolean.class);

        System.out.println(model);
    }
}

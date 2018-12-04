package org.baeldung.gson.primitives.booleanvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBoolean;

public class JsonYesRepresentationBooleanValue {
    public static void main(String[] args) {
        // It fails silently.
        String json = "{\"value\": yes}";
        Gson gson = new Gson();

        GsonBoolean model = gson.fromJson(json, GsonBoolean.class);

        System.out.println(model);
    }
}

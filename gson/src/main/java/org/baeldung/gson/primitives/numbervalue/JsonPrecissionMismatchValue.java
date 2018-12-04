package org.baeldung.gson.primitives.numbervalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonFloat;

public class JsonPrecissionMismatchValue {
    public static void main(String[] args) {
        String json = "{\"value\": 12.123456789123456}";
        Gson gson = new Gson();

        GsonFloat model = gson.fromJson(json, GsonFloat.class);

        System.out.println(model);
    }
}

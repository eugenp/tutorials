package org.baeldung.gson.primitives.specialvalue;

import com.google.gson.Gson;
import org.baeldung.gson.primitives.models.GsonBitString;

public class JsonNullValue {
    public static void main(String[] args) {
        // The field will just be ignored.
        Gson gson = new Gson();
        String json = "{\"value\": null}";
        GsonBitString model = gson.fromJson(json, GsonBitString.class);

        System.out.println(model);
    }
}

package com.baeldung.gson_primitive_types.char_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.baeldung.gson_primitive_types.models.GsonLatinChar;
import com.google.gson.Gson;

public class JsonUnicodeCharValue {
    public static void main(String[] args) {
        // The field is converted.
        Gson gson = new Gson();
        String json = "{\"value\": \"\\u00AE\"}";
        GsonLatinChar model = gson.fromJson(json, GsonLatinChar.class);

        System.out.println(model);
    }
}

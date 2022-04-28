package com.baeldung.jsonvalidation;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;

public class GsonValidator {

    public boolean isValid(String json) {
        final JsonParser parser = new JsonParser();
        try {
            parser.parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    public boolean isValidStrict(String json) {
        final TypeAdapter<JsonElement> strictAdapter = new Gson().getAdapter(JsonElement.class);
        try {
            strictAdapter.fromJson(json);
            return true;
        } catch (JsonSyntaxException | IOException e) {
            return false;
        }
    }
}

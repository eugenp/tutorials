package com.baeldung.gson.deserialization;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class FooDeserializer implements JsonDeserializer<Foo[]> {

    @Override
    public Foo[] deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final JsonArray jArray = json.getAsJsonArray();
        final Foo[] scArray = new Foo[jArray.size()];
        int index = 0;
        for (final JsonElement jElement : jArray) {
            final int i = jElement.getAsJsonObject().get("intValue").getAsInt();
            final String s = jElement.getAsJsonObject().get("stringValue").getAsString();
            scArray[index++] = new Foo(i, s);
        }
        return scArray;
    }

}
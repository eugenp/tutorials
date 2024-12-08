package com.baeldung.gson.deserialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class FooDeserializerFromJsonWithDifferentFields implements JsonDeserializer<Foo> {

    @Override
    public Foo deserialize(final JsonElement jElement, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jObject = jElement.getAsJsonObject();
        final int intValue = jObject.get("valueInt").getAsInt();
        final String stringValue = jObject.get("valueString").getAsString();
        return new Foo(intValue, stringValue);
    }

}

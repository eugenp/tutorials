package com.baeldung.gson.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class IgnoringFieldsNotMatchingCriteriaSerializer implements JsonSerializer<SourceClass> {
    @Override
    public JsonElement serialize(SourceClass src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();

        // Criteria: intValue >= 0
        if (src.getIntValue() >= 0) {
            String intValue = "intValue";
            jObject.addProperty(intValue, src.getIntValue());
        }

        String stringValue = "stringValue";
        jObject.addProperty(stringValue, src.getStringValue());

        return jObject;
    }

}

package org.baeldung.gson.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;


public class TargetClassDeserializer implements JsonDeserializer<TargetClass> {

    @Override
    public TargetClass deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = jElement.getAsJsonObject();
        int intValue = jObject.get("valueInt").getAsInt();
        String stringValue = jObject.get("valueString").getAsString();
        return new TargetClass(intValue, stringValue);
    }
}

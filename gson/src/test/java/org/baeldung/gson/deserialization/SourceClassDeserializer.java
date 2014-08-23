package org.baeldung.gson.deserialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class SourceClassDeserializer implements JsonDeserializer<SourceClass[]> {

    @Override
    public SourceClass[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jArray = json.getAsJsonArray();
        SourceClass[] scArray = new SourceClass[jArray.size()];
        int index = 0;
        for (JsonElement jElement : jArray) {
            int i = jElement.getAsJsonObject().get("intValue").getAsInt();
            String s = jElement.getAsJsonObject().get("stringValue").getAsString();
            scArray[index++] = new SourceClass(i, s);
        }
        return scArray;
    }
}
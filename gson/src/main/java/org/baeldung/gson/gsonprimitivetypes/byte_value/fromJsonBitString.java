package com.baeldung.gson_primitive_types.byte_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.*;

import java.lang.reflect.Type;

public class fromJsonBitString {
    public static void main(String[] args) {
        String json = "{\"value\": \"1111\"}";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GsonBitString.class, new GsonBitStringDeserializer());

        Gson gson = gsonBuilder.create();

        System.out.println(gson.fromJson(json, GsonBitString.class));
    }

    static class GsonBitStringDeserializer implements JsonDeserializer<GsonBitString> {
        @Override public GsonBitString deserialize(JsonElement jsonElement,
            Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            GsonBitString gsonBitString = new GsonBitString();
            gsonBitString.value = (byte) Integer.parseInt(
                jsonElement.getAsJsonObject().getAsJsonPrimitive("value").getAsString(), 2);
            return gsonBitString;
        }
    }
}

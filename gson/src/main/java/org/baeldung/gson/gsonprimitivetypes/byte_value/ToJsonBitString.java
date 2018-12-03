package com.baeldung.gson_primitive_types.byte_value;

import com.baeldung.gson_primitive_types.models.GsonBitString;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ToJsonBitString {
    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GsonBitString.class, new GsonBitStringSerializer());

        Gson gson = builder.create();
        GsonBitString model = new GsonBitString();
        model.value = (byte) 0b1111;

        System.out.println(gson.toJson(model));
    }

    static class GsonBitStringSerializer implements JsonSerializer<GsonBitString> {
        @Override public JsonElement serialize(GsonBitString gsonBundle, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", Integer.toBinaryString(gsonBundle.value));
            return jsonObject;
        }
    }
}

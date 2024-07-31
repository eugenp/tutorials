package com.baeldung.gson.polymorphic;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ShapeTypeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {
    @Override
    public JsonElement serialize(Shape shape, Type type, JsonSerializationContext context) {
        JsonElement elem = new Gson().toJsonTree(shape);
        elem.getAsJsonObject().addProperty("type", shape.getClass().getName());
        return elem;
    }

    @Override
    public Shape deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String typeName = jsonObject.get("type").getAsString();

        try {
            Class<? extends Shape> cls = (Class<? extends Shape>) Class.forName(typeName);
            return new Gson().fromJson(json, cls);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}

package com.baeldung.jsonminifier;

import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonMinifier {
    public String removeExtraWhitespace(String json) {
        StringBuilder result = new StringBuilder(json.length());
        boolean inQuotes = false;
        boolean escapeMode = false;

        for (char character : json.toCharArray()) {
            if (escapeMode) {
                result.append(character);
                escapeMode = false;
            } else if (character == '"') {
                inQuotes = !inQuotes;
                result.append(character);
            } else if (character == '\\') {
                escapeMode = true;
                result.append(character);
            } else if (!inQuotes && character == ' ') {
                continue;
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    public String removeExtraWhitespaceUsingJackson(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.writeValueAsString(jsonNode);
    }

    public String removeWhitespacesUsingGson(String json) {
        Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new StringSerializer()).create();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        return gson.toJson(jsonElement);
    }

    class StringSerializer implements JsonSerializer<String> {
        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.trim());
        }
    }
}

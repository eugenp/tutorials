package com.baeldung.jackson.jsonnode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class GetAllKeysFromJSON {

    public static List<String> getKeysInJsonUsingMaps(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            Map<String, Object> jsonElements = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            getAllKeys(jsonElements, keys);
            return keys;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return keys;
    }

    public static void getAllKeys(Map<String, Object> jsonElements, List<String> keys) {

        jsonElements.entrySet()
            .forEach(entry -> {
                keys.add(entry.getKey());
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) entry.getValue();
                    getAllKeys(map, keys);
                } else if (entry.getValue() instanceof List) {
                    List<?> list = (List<?>) entry.getValue();
                    list.forEach(listEntry -> {
                        if (listEntry instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) listEntry;
                            getAllKeys(map, keys);
                        }
                    });
                }
            });
    }

    public static List<String> getKeysInJsonUsingJsonNodeFieldNames(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            Iterator<String> iterator = jsonNode.fieldNames();
            iterator.forEachRemaining(e -> keys.add(e));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return keys;
    }

    public static List<String> getAllKeysInJsonUsingJsonNodeFieldNames(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            getAllKeysUsingJsonNodeFieldNames(jsonNode, keys);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return keys;
    }

    public static List<String> getAllKeysInJsonUsingJsonNodeFields(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            getAllKeysUsingJsonNodeFields(jsonNode, keys);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return keys;
    }

    public static void getAllKeysUsingJsonNodeFields(JsonNode jsonNode, List<String> keys) {

        if (jsonNode.isObject()) {
            Iterator<Entry<String, JsonNode>> fields = jsonNode.fields();

            fields.forEachRemaining(field -> {
                keys.add(field.getKey());
                getAllKeysUsingJsonNodeFieldNames((JsonNode) field.getValue(), keys);
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                getAllKeysUsingJsonNodeFieldNames(node, keys);
            });
        }

    }

    public static void getAllKeysUsingJsonNodeFieldNames(JsonNode jsonNode, List<String> keys) {

        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();

            fieldNames.forEachRemaining(fieldName -> {
                keys.add(fieldName);
                getAllKeysUsingJsonNodeFieldNames(jsonNode.get(fieldName), keys);
            });
        } else if (jsonNode.isArray()) {
            ArrayNode arrayField = (ArrayNode) jsonNode;
            arrayField.forEach(node -> {
                getAllKeysUsingJsonNodeFieldNames(node, keys);
            });
        }

    }

    public static List<String> getKeysInJsonUsingJsonParser(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            JsonParser jsonParser = jsonNode.traverse();
            while (!jsonParser.isClosed()) {
                if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
                    keys.add((jsonParser.getCurrentName()));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keys;
    }

    public static List<String> getKeysInJsonUsingJsonParser(String json) {
        List<String> keys = new ArrayList<>();

        try {
            JsonFactory factory = new JsonFactory();
            JsonParser jsonParser = factory.createParser(json);
            while (!jsonParser.isClosed()) {
                if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
                    keys.add((jsonParser.getCurrentName()));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keys;
    }
}

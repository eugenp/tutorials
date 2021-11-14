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

/**
 * This class contains different ways to traverse a JSON string and fetch key names for all objects.
 * 
 * @author Priya Soni
 *
 */
public class GetAllKeysFromJSON {

    public static void main(String[] args) {
        String json = "{\r\n" + "   \"FirstName\":\"Craig\",\r\n" + "   \"MiddleName\":null,\r\n" + "   \"Age\":10,\r\n" + "   \"BookInterests\":[\r\n" + "      {\r\n" + "         \"Book\":\"The Kite Runner\",\r\n"
            + "         \"Author\":\"Khaled Hosseini\"\r\n" + "      },\r\n" + "      {\r\n" + "         \"Book\":\"Harry Potter\",\r\n" + "         \"Author\":\"J. K. Rowling\"\r\n" + "      }\r\n" + "   ],\r\n" + "   \"TravelInterests\":[\r\n"
            + "      {\r\n" + "         \"India\":{\r\n" + "            \"Beaches\":\"Goa\",\r\n" + "            \"Mountains\":\"Himachal Pradesh\"\r\n" + "         },\r\n" + "         \"Europe\":{\r\n" + "            \"Heritage\":\"Greece\",\r\n"
            + "            \"Lakes\":\"Italy\"\r\n" + "         }\r\n" + "      }\r\n" + "   ],\r\n" + "   \"CityInterests\":[\r\n" + "      \"Jaipur\",\r\n" + "      \"NYC\",\r\n" + "      \"Paris\"\r\n" + "   ],\r\n" + "   \"FoodInterests\" :{\r\n"
            + "      \"Breakfast\":[\r\n" + "         {\r\n" + "            \"Bread\":\"Whole wheat\",\r\n" + "            \"Beverage\":\"Fruit juice\"\r\n" + "         },\r\n" + "         {\r\n" + "            \"Sandwich\":\"Vegetable Sandwich\",\r\n"
            + "            \"Beverage\":\"Coffee\"\r\n" + "         }\r\n" + "      ],\r\n" + "      \"Lunch\":null,\r\n" + "      \"Dinner\":{\r\n" + "         \"Rice\":\"Steamed Rice\",\r\n" + "         \"Soup\":\"Lentil soup\"\r\n" + "      }\r\n"
            + "   }\r\n" + "}";

        ObjectMapper mapper = new ObjectMapper();
        List<String> keys = getKeysInJsonUsingJsonNodeFieldNames(json, mapper);
        System.out.println(keys);
        keys = getAllKeysInJsonUsingJsonNodeFieldNames(json, mapper);
        System.out.println(keys);
        keys = getAllKeysInJsonUsingJsonNodeFields(json, mapper);
        System.out.println(keys);
        keys = getKeysInJsonUsingJsonParser(json, mapper);
        System.out.println(keys);
        keys = getKeysInJsonUsingJsonParser(json);
        System.out.println(keys);
        keys = getKeysInJsonUsingMaps(json, mapper);
        System.out.println(keys);

        // Top level keys : [FirstName, MiddleName, Age, BookInterests, TravelInterests, CityInterests, FoodInterests]
        // All keys: [FirstName, MiddleName, Age, BookInterests, Book, Author, Book, Author, TravelInterests, India, Beaches, Mountains, Europe, Heritage, Lakes, CityInterests, FoodInterests, Breakfast, Bread, Beverage, Sandwich, Beverage, Lunch, Dinner, Rice,
        // Soup]

    }

    private static List<String> getKeysInJsonUsingMaps(String json, ObjectMapper mapper) {
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

    private static void getAllKeys(Map<String, Object> jsonElements, List<String> keys) {

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

    private static List<String> getKeysInJsonUsingJsonNodeFieldNames(String json, ObjectMapper mapper) {
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

    private static List<String> getAllKeysInJsonUsingJsonNodeFieldNames(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            getAllKeysUsingJsonNodeFieldNames(jsonNode, keys);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return keys;
    }

    private static List<String> getAllKeysInJsonUsingJsonNodeFields(String json, ObjectMapper mapper) {
        List<String> keys = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(json);
            getAllKeysUsingJsonNodeFields(jsonNode, keys);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return keys;
    }

    private static void getAllKeysUsingJsonNodeFields(JsonNode jsonNode, List<String> keys) {

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

    private static void getAllKeysUsingJsonNodeFieldNames(JsonNode jsonNode, List<String> keys) {

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

    private static List<String> getKeysInJsonUsingJsonParser(String json, ObjectMapper mapper) {
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

    private static List<String> getKeysInJsonUsingJsonParser(String json) {
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

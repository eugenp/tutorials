package com.baeldung.jsonnodetocollection;

import com.baeldung.jsonnodetocollection.dto.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.management.BadAttributeValueExpException;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonNodeConversionUtil {

    public static List<Person> manualJsonNodeToList(JsonNode jsonNode) {
        List<Person> people = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Person person = new Person(node.get("name").asText(), node.get("age").asInt());
            people.add(person);
        }

        return people;
    }

    public static Map<String, Person> manualJsonNodeToMap(JsonNode jsonNode) {
        Map<String, Person> mapOfIdToPerson = new HashMap<>();
        jsonNode.fields()
          .forEachRemaining(node -> mapOfIdToPerson.put(node.getKey(),
            new Person(node.getValue().get("name").asText(), node.getValue().get("age").asInt())));

        return mapOfIdToPerson;
    }

    public static List<Person> readValueJsonNodeToList(JsonNode jsonNode) throws IOException {
        TypeReference<List<Person>> typeReferenceList = new TypeReference<List<Person>>() {};
        return new ObjectMapper().readValue(jsonNode.traverse(), typeReferenceList);
    }

    public static Map<String, Person> readValueJsonNodeToMap(JsonNode jsonNode) throws IOException {
        TypeReference<Map<String, Person>> typeReferenceMap = new TypeReference<Map<String, Person>>() {};
        return new ObjectMapper().readValue(jsonNode.traverse(), typeReferenceMap);
    }

    public static List<Person> convertValueJsonNodeToList(JsonNode jsonNode) {
        TypeReference<List<Person>> typeReferenceList = new TypeReference<List<Person>>() {};
        return new ObjectMapper().convertValue(jsonNode, typeReferenceList);
    }

    public static Map<String, Person> convertValueJsonNodeToMap(JsonNode jsonNode) {
        TypeReference<Map<String, Person>> typeReferenceMap = new TypeReference<Map<String, Person>>() {};
        return new ObjectMapper().convertValue(jsonNode, typeReferenceMap);
    }
}

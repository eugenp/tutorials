package com.baeldung.jsonnodetocollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.jsonnodetocollection.dto.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

 public class JsonNodeConversionUtil {

     static List<Person> manualJsonNodeToList(JsonNode personsNode) {
        List<Person> people = new ArrayList<>();
         for (JsonNode node : personsNode) {
            Person person = new Person(node.get("name").asText(), node.get("age").asInt());
            people.add(person);
        }

        return people;
    }

     static Map<String, Person> manualJsonNodeToMap(JsonNode idToPersonsNode) {
        Map<String, Person> mapOfIdToPerson = new HashMap<>();
         idToPersonsNode.fields()
          .forEachRemaining(node -> mapOfIdToPerson.put(node.getKey(),
            new Person(node.getValue().get("name").asText(), node.getValue().get("age").asInt())));

        return mapOfIdToPerson;
    }

     static List<Person> readValueJsonNodeToList(JsonNode personsNode) throws IOException {
        TypeReference<List<Person>> typeReferenceList = new TypeReference<List<Person>>() {};
         return new ObjectMapper().readValue(personsNode.traverse(), typeReferenceList);
    }

     static Map<String, Person> readValueJsonNodeToMap(JsonNode idToPersonsNode) throws IOException {
        TypeReference<Map<String, Person>> typeReferenceMap = new TypeReference<Map<String, Person>>() {};
         return new ObjectMapper().readValue(idToPersonsNode.traverse(), typeReferenceMap);
    }

     static List<Person> convertValueJsonNodeToList(JsonNode personsNode) {
        TypeReference<List<Person>> typeReferenceList = new TypeReference<List<Person>>() {};
         return new ObjectMapper().convertValue(personsNode, typeReferenceList);
    }

     static Map<String, Person> convertValueJsonNodeToMap(JsonNode idToPersonsNode) {
        TypeReference<Map<String, Person>> typeReferenceMap = new TypeReference<Map<String, Person>>() {};
         return new ObjectMapper().convertValue(idToPersonsNode, typeReferenceMap);
    }
}

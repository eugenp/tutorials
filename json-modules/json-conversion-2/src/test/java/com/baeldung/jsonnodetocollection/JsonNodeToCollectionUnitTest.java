package com.baeldung.jsonnodetocollection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.jsonnodetocollection.dto.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonNodeToCollectionUnitTest {

    public static String jsonString = "{\"persons\":[{\"name\":\"John\",\"age\":30},{\"name\":\"Alice\",\"age\":25}],\"idToPerson\":{\"1234\":{\"name\":\"John\",\"age\":30},\"1235\":{\"name\":\"Alice\",\"age\":25}}}";

    static JsonNode completeJson;
    static JsonNode personsNode;
    static JsonNode idToPersonNode;

    @BeforeAll
    static void setup() throws JsonProcessingException {
        completeJson = new ObjectMapper().readTree(jsonString);
        personsNode = completeJson.get("persons");
        idToPersonNode = completeJson.get("idToPerson");
    }

    @Test
    void givenJsonNode_whenConvertingToList_thenFieldsAreCorrect() throws IOException {

        List<Person> personList1 = JsonNodeConversionUtil.manualJsonNodeToList(personsNode);
        List<Person> personList2 = JsonNodeConversionUtil.readValueJsonNodeToList(personsNode);
        List<Person> personList3 = JsonNodeConversionUtil.convertValueJsonNodeToList(personsNode);

        validateList(personList1);
        validateList(personList2);
        validateList(personList3);
    }


    @Test
    void givenJsonNode_whenConvertingToMap_thenFieldsAreCorrect() throws IOException {

        Map<String, Person> personMap1 = JsonNodeConversionUtil.manualJsonNodeToMap(idToPersonNode);
        Map<String, Person> personMap2 = JsonNodeConversionUtil.readValueJsonNodeToMap(idToPersonNode);
        Map<String, Person> personMap3 = JsonNodeConversionUtil.convertValueJsonNodeToMap(idToPersonNode);

        validateMapOfPersons(personMap1);
        validateMapOfPersons(personMap2);
        validateMapOfPersons(personMap3);
    }

    @Test
    void givenJsonNode_whenConvertingToListWithCustomDeserializer_thenFieldsAreCorrect() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(List.class, new CustomPersonListDeserializer());
        objectMapper.registerModule(module);
        List<Person> personList = objectMapper.convertValue(personsNode, new TypeReference<List<Person>>() {
          });

        validateList(personList);
    }

    private void validateList(List<Person> personList) {
        assertEquals(2, personList.size());

        Person person1 = personList.get(0);
        assertEquals("John", person1.getName());
        assertEquals(30, person1.getAge());

        Person person2 = personList.get(1);
        assertEquals("Alice", person2.getName());
        assertEquals(25, person2.getAge());
    }

    private void validateMapOfPersons(Map<String, Person> map) {
        assertEquals(2, map.size());

        Person person1 = map.get("1234");
        assertEquals("John", person1.getName());
        assertEquals(30, person1.getAge());

        Person person2 = map.get("1235");
        assertEquals("Alice", person2.getName());
        assertEquals(25, person2.getAge());
    }
}

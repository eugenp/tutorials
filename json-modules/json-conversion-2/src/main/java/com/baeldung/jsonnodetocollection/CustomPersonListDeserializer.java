package com.baeldung.jsonnodetocollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.jsonnodetocollection.dto.Person;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomPersonListDeserializer extends JsonDeserializer<List<Person>> {
    @Override
    public List<Person> deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        List<Person> personList = new ArrayList<>();
        JsonNode personsNode = objectMapper.readTree(p);
        for (JsonNode node : personsNode) {
            personList.add(objectMapper.readValue(node.traverse(), Person.class));
        }

        return personList;
    }
}
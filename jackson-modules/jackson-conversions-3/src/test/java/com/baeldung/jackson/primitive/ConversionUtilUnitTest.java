package com.baeldung.jackson.primitive;

import com.baeldung.jackson.primitive.dtos.PersonDTO;
import com.baeldung.jackson.primitive.dtos.PersonDTOWithCustomDeserializer;
import com.baeldung.jackson.primitive.dtos.PersonDTOWithType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static com.baeldung.jackson.primitive.ConversionUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class ConversionUtilUnitTest {

    @Test
    void givenJsonWithDifferentValueTypes_whenDeserialize_thenSuccess() throws JsonProcessingException {
        String json = "{\"person\": [{\"key\": \"name\", \"value\": \"John\"}, {\"key\": \"id\", \"value\": 25}]}";
        PersonDTOWithCustomDeserializer personDTO = readJson(json);
        assertEquals(String.class, personDTO.getPerson().get(0).getValue().getClass());
        assertEquals(Long.class, personDTO.getPerson().get(1).getValue().getClass());
    }

    @Test
    void givenJsonWithDifferentValueTypes_whenDeserializeWithLongForInts_thenSuccess() throws JsonProcessingException {
        String json = "{\"person\": [{\"key\": \"name\", \"value\": \"John\"}, {\"key\": \"id\", \"value\": 25}]}";
        PersonDTO personDTO = readJsonWithLongForInts(json);
        assertEquals(String.class, personDTO.getPerson().get(0).getValue().getClass());
        assertEquals(Long.class, personDTO.getPerson().get(1).getValue().getClass());
    }

    @Test
    void givenJsonWithDifferentValueTypes_whenDeserializeWithTypeInfo_thenSuccess() throws JsonProcessingException {
        String json = "{\"person\": [{\"key\": \"name\", \"type\": \"string\", \"value\": \"John\"}, {\"key\": \"id\", \"type\": \"long\", \"value\": 25}, {\"key\": \"age\", \"type\": \"int\", \"value\": 30}]}";
        PersonDTOWithType personDTO = readJsonWithValueType(json);
        assertEquals(String.class, personDTO.getPerson().get(0).getValue().getClass());
        assertEquals(Long.class, personDTO.getPerson().get(1).getValue().getClass());
        assertEquals(Integer.class, personDTO.getPerson().get(2).getValue().getClass());
    }

}
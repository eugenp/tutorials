package com.baeldung.jackson.specifictype;

import com.baeldung.jackson.specifictype.dtos.PersonDTO;
import com.baeldung.jackson.specifictype.dtos.PersonDTOWithCustomDeserializer;
import com.baeldung.jackson.specifictype.dtos.PersonDTOWithType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionUtil {

    public static PersonDTOWithCustomDeserializer readJsonWithCustomDeserializer(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PersonDTOWithCustomDeserializer.class);
    }

    public static PersonDTO readJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PersonDTO.class);
    }

    public static PersonDTO readJsonWithLongForInts(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        return mapper.readValue(json, PersonDTO.class);
    }

    public static PersonDTOWithType readJsonWithValueType(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, PersonDTOWithType.class);
    }
}

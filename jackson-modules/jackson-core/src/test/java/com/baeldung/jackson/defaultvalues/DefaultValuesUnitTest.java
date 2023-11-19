package com.baeldung.jackson.defaultvalues;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultValuesUnitTest {

    @Test
    public void givenAClassWithADefaultValue_whenReadingJsonWithoutOptionalValue_thenExpectDefaultValueInResult() throws JsonProcessingException {
        String noOptionalField = "{\"required\": \"value\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        NonAnnotatedDefaultValue createdObject = objectMapper.readValue(noOptionalField, NonAnnotatedDefaultValue.class);
        assert(createdObject.getRequired()).equals("value");
        assert(createdObject.getOptional()).equals("defaultValue");
    }

    @Test
    public void givenAClassWithASetter_whenReadingJsonWithNullOptionalValue_thenExpectDefaultValueInResult() throws JsonProcessingException {
        String nullOptionalField = "{\"required\": \"value\", \"optional\": null}";
        ObjectMapper objectMapper = new ObjectMapper();
        SetterDefaultValue createdObject = objectMapper.readValue(nullOptionalField, SetterDefaultValue.class);
        assert(createdObject.getRequired()).equals("value");
        assert(createdObject.getOptional()).equals("valueIfNull");
    }

    @Test
    public void givenAClassWithAJsonSetterNullsSkip_whenReadingJsonWithNullOptionalValue_thenExpectDefaultValueInResult() throws JsonProcessingException {
        String nullOptionalField = "{\"required\": \"value\", \"optional\": null}";
        ObjectMapper objectMapper = new ObjectMapper();
        NullsSkipDefaultValue createdObject = objectMapper.readValue(nullOptionalField, NullsSkipDefaultValue.class);
        assert(createdObject.getRequired()).equals("value");
        assert(createdObject.getOptional()).equals("defaultValue");
    }

}

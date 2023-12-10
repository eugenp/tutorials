package com.baeldung.mappingexception;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import com.baeldung.exceptions.EmptyObject;
import com.baeldung.exceptions.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMappingExceptionUnitTest {

    @Test(expected = JsonMappingException.class)
    public void givenObjectHasNoAccessors_whenSerializing_thenException() throws JsonProcessingException {
        final String dtoAsString = new ObjectMapper().writeValueAsString(new MyDtoNoAccessors());

        assertThat(dtoAsString, notNullValue());
    }

    @Test
    public void givenObjectHasNoAccessors_whenSerializingWithPrivateFieldsVisibility_thenNoException() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        final String dtoAsString = objectMapper.writeValueAsString(new MyDtoNoAccessors());

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
    }

    @Test
    public void givenObjectHasNoAccessorsButHasVisibleFields_whenSerializing_thenNoException() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String dtoAsString = objectMapper.writeValueAsString(new MyDtoNoAccessorsAndFieldVisibility());

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
    }

    @Test
    public void givenJsonWithInvalidList_whenDeserializing_thenThrowException() throws JsonProcessingException {
        final String json = "{\"name\":\"Netherlands\",\"cities\":{\"Amsterdam\", \"Tamassint\"}}";
        final ObjectMapper mapper = new ObjectMapper();

        Exception exception = assertThrows(JsonMappingException.class, () -> mapper.reader()
            .forType(Country.class)
            .readValue(json));

        assertTrue(exception.getMessage()
            .contains("Cannot deserialize value of type `java.util.ArrayList<java.lang.String>`"));
    }

    @Test
    public void givenJsonWithValidList_whenDeserializing_thenCorrect() throws JsonProcessingException {
        final String json = "{\"name\":\"Netherlands\",\"cities\":[\"Amsterdam\", \"Tamassint\"]}";
        final ObjectMapper mapper = new ObjectMapper();

        Country country = mapper.reader()
            .forType(Country.class)
            .readValue(json);

        assertEquals("Netherlands", country.getName());
        assertEquals(Arrays.asList("Amsterdam", "Tamassint"), country.getCities());
    }

    @Test
    public void givenEmptyBean_whenSerializing_thenCorrect() throws JsonProcessingException {
        // Create an ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        // Disable fail_on_empty_beans during serialization
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // Create an empty object
        EmptyObject emptyObject = new EmptyObject();
        // Serialize the empty object
        String json = objectMapper.writeValueAsString(emptyObject);
        // Verify that serialization is successful
        assertEquals("{}", json);
    }

}

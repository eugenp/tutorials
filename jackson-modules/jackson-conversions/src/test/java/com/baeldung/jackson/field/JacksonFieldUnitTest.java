package com.baeldung.jackson.field;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFieldUnitTest {

    @Test
    public final void givenDifferentAccessLevels_whenPublic_thenSerializable() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoAccessLevel dtoObject = new MyDtoAccessLevel();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, not(containsString("stringValue")));
        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, not(containsString("floatValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenDifferentAccessLevels_whenGetterAdded_thenSerializable() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoWithGetter dtoObject = new MyDtoWithGetter();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, not(containsString("intValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenDifferentAccessLevels_whenGetterAdded_thenDeserializable() throws IOException {
        final String jsonAsString = "{\"stringValue\":\"dtoString\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoWithGetter dtoObject = mapper.readValue(jsonAsString, MyDtoWithGetter.class);

        assertNotNull(dtoObject);
        assertThat(dtoObject.getStringValue(), equalTo("dtoString"));
    }

    @Test
    public final void givenDifferentAccessLevels_whenSetterAdded_thenDeserializable() throws IOException {
        final String jsonAsString = "{\"intValue\":1}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoWithSetter dtoObject = mapper.readValue(jsonAsString, MyDtoWithSetter.class);

        assertNotNull(dtoObject);
        assertThat(dtoObject.accessIntValue(), equalTo(1));
    }

    @Test
    public final void givenDifferentAccessLevels_whenSetterAdded_thenStillNotSerializable() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoWithSetter dtoObject = new MyDtoWithSetter();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, not(containsString("intValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenDifferentAccessLevels_whenSetVisibility_thenSerializable() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        final MyDtoAccessLevel dtoObject = new MyDtoAccessLevel();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

}

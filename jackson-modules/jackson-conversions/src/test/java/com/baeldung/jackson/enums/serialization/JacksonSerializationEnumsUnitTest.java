package com.baeldung.jackson.enums.serialization;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.baeldung.jackson.enums.withEnum.DistanceEnumSimple;
import com.baeldung.jackson.enums.withEnum.DistanceEnumWithJsonFormat;
import com.baeldung.jackson.enums.withEnum.DistanceEnumWithValue;
import com.baeldung.jackson.enums.withEnum.MyDtoWithEnumCustom;
import com.baeldung.jackson.enums.withEnum.MyDtoWithEnumJsonFormat;
import com.baeldung.jackson.enums.serialization.Distance;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializationEnumsUnitTest {

    // tests - simple enum

    @Test
    public final void whenSerializingASimpleEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String enumAsString = mapper.writeValueAsString(DistanceEnumSimple.MILE);

        assertThat(enumAsString, containsString("MILE"));
    }

    // tests - enum with main value

    @Test
    public final void whenSerializingAEnumWithValue_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String enumAsString = mapper.writeValueAsString(DistanceEnumWithValue.MILE);

        assertThat(enumAsString, is("1609.34"));
    }

    // tests - enum

    @Test
    public final void whenSerializingAnEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String enumAsString = mapper.writeValueAsString(DistanceEnumWithJsonFormat.MILE);

        assertThat(enumAsString, containsString("\"meters\":1609.34"));
    }

    @Test
    public final void whenSerializingEntityWithEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String enumAsString = mapper.writeValueAsString(new MyDtoWithEnumJsonFormat("a", 1, true, DistanceEnumWithJsonFormat.MILE));

        assertThat(enumAsString, containsString("\"meters\":1609.34"));
    }

    @Test
    public final void whenSerializingArrayOfEnums_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(new DistanceEnumWithJsonFormat[] { DistanceEnumWithJsonFormat.MILE, DistanceEnumWithJsonFormat.KILOMETER });

        assertThat(json, containsString("\"meters\":1609.34"));
    }

    // tests - enum with custom serializer

    @Test
    public final void givenCustomSerializer_whenSerializingEntityWithEnum_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String enumAsString = mapper.writeValueAsString(new MyDtoWithEnumCustom("a", 1, true, Distance.MILE));

        assertThat(enumAsString, containsString("\"meters\":1609.34"));
    }

}

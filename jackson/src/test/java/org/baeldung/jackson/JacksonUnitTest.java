package org.baeldung.jackson;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.common.collect.Lists;

public class JacksonUnitTest {

    // tests - single entity to json

    @Test
    public final void givenOnlyNonDefaultValuesAreSerialized_whenDtoHasOnlyDefaultValues_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new FooDto());

        assertThat(dtoAsString, not(containsString("intValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenOnlyNonDefaultValuesAreSerialized_whenDtoHasNonDefaultValue_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final FooDto dtoObject = new FooDto();
        dtoObject.setBooleanValue(true);

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenFieldIsIgnored_whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final BarDto dtoObject = new BarDto();
        dtoObject.setBooleanValue(true);

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    // tests - multiple entities to json

    @Test
    public final void whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));

        final ObjectMapper mapper = new ObjectMapper();
        final String dtosAsString = mapper.writeValueAsString(listOfDtos);

        System.out.println(dtosAsString);
    }

    // tests - json to single entity

    @Test
    public final void whenDeserializingAJsonToAClass_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
    }

    @Test(expected = UnrecognizedPropertyException.class)
    public final void givenJsonHasUnkownValues_whenDeserializingAJsonToAClass_thenExceptionIsThrown() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true,\"stringValue2\":\"something\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    @Test
    public final void givenJsonHasUnkownValuesButJacksonIsIgnoringUnkownFields_whenDeserializingAJsonToAClass_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true,\"stringValue2\":\"something\"}";
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    @Test
    public final void givenJsonHasUnkownValuesButUnkownFieldsAreIgnoredOnClass_whenDeserializingAJsonToAClass_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true,\"stringValue2\":\"something\"}";
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final MyDtoIgnoreUnkown readValue = mapper.readValue(jsonAsString, MyDtoIgnoreUnkown.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    // tests - json to multiple entities

}

/*
Article Ideas:
- Deserializing with a custom JsonParser
- Jackson Ignore: ignore specific fields at field level, at class level,
 */

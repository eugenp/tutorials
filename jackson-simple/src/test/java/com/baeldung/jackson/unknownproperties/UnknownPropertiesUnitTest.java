package com.baeldung.jackson.unknownproperties;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UnknownPropertiesUnitTest {

    @Test
    public final void givenNotAllFieldsHaveValuesInJson_whenDeserializingAJsonToAClass_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"booleanValue\":true}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
    }

    // tests - json with unknown fields

    @Test(expected = UnrecognizedPropertyException.class)
    public final void givenJsonHasUnknownValues_whenDeserializingAJsonToAClass_thenExceptionIsThrown() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true,\"stringValue2\":\"something\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    @Test
    public final void givenJsonHasUnknownValuesButJacksonIsIgnoringUnknownFields_whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = // @formatter:off
                "{\"stringValue\":\"a\"," +
                "\"intValue\":1," +
                "\"booleanValue\":true," +
                "\"stringValue2\":\"something\"}"; // @formatter:on
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    @Test
    public final void givenJsonHasUnknownValuesButUnknownFieldsAreIgnoredOnClass_whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = // @formatter:off
                "{\"stringValue\":\"a\"," +
                "\"intValue\":1," +
                "\"booleanValue\":true," +
                "\"stringValue2\":\"something\"}"; // @formatter:on
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoIgnoreUnknown readValue = mapper.readValue(jsonAsString, MyDtoIgnoreUnknown.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

}

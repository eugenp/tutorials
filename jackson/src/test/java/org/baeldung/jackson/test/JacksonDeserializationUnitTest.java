package org.baeldung.jackson.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import dtos.MyDto;
import dtos.MyDtoIgnoreUnkown;

public class JacksonDeserializationUnitTest {

    @Test
    public final void whenDeserializingAJsonToAClass_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"a\",\"intValue\":1,\"booleanValue\":true}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDto readValue = mapper.readValue(jsonAsString, MyDto.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
    }

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
    public final void givenJsonHasUnkownValuesButJacksonIsIgnoringUnkownFields_whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString =// @formatter:off
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
    public final void givenJsonHasUnkownValuesButUnkownFieldsAreIgnoredOnClass_whenDeserializing_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString =// @formatter:off
                "{\"stringValue\":\"a\"," +
                "\"intValue\":1," +
                "\"booleanValue\":true," +
                "\"stringValue2\":\"something\"}"; // @formatter:on
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoIgnoreUnkown readValue = mapper.readValue(jsonAsString, MyDtoIgnoreUnkown.class);

        assertNotNull(readValue);
        assertThat(readValue.getStringValue(), equalTo("a"));
        assertThat(readValue.isBooleanValue(), equalTo(true));
        assertThat(readValue.getIntValue(), equalTo(1));
    }

    // to JsonNode

    @Test
    public final void whenParsingJsonStringIntoJsonNode_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualObj = mapper.readTree(jsonString);

        assertNotNull(actualObj);
    }

    @Test
    public final void givenUsingLowLevelDetails_whenParsingJsonStringIntoJsonNode_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getFactory();
        final JsonParser parser = factory.createParser(jsonString);
        final JsonNode actualObj = mapper.readTree(parser);

        assertNotNull(actualObj);
    }

    @Test
    public final void givenTheJsonNode_whenRetrievingDataFromId_thenCorrect() throws JsonParseException, IOException {
        final String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualObj = mapper.readTree(jsonString);

        // When
        final JsonNode jsonNode1 = actualObj.get("k1");
        assertThat(jsonNode1.textValue(), equalTo("v1"));
    }

}

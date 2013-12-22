package org.baeldung.jackson.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.baeldung.jackson.ignore.MyDto;
import org.baeldung.jackson.ignore.MyDtoIgnoreUnkown;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JacksonDeserializationUnitTest {

    // tests - json to single entity

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

        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(final DeserializationContext ctxt, final JsonParser jp, final JsonDeserializer<?> deserializer, final Object beanOrClass, final String propertyName) throws IOException, JsonProcessingException {
                return super.handleUnknownProperty(ctxt, jp, deserializer, beanOrClass, propertyName);
            }
        });
    }

}

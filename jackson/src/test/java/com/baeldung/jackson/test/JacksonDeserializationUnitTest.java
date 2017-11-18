package com.baeldung.jackson.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.baeldung.jackson.deserialization.ItemDeserializer;
import com.baeldung.jackson.dtos.Item;
import com.baeldung.jackson.dtos.ItemWithSerializer;
import com.baeldung.jackson.dtos.MyDto;
import com.baeldung.jackson.dtos.ignore.MyDtoIgnoreUnknown;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.module.SimpleModule;

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

    // custom deserialization

    @Test
    public final void whenDeserializingTheStandardRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":{\"id\":2,\"name\":\"theUser\"}}";

        final Item readValue = new ObjectMapper().readValue(json, Item.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public final void whenDeserializingANonStandardRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"createdBy\":2}";
        final ObjectMapper mapper = new ObjectMapper();

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer());
        mapper.registerModule(module);

        final Item readValue = mapper.readValue(json, Item.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public final void givenDeserializerIsOnClass_whenDeserializingCustomRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":2}";

        final ItemWithSerializer readValue = new ObjectMapper().readValue(json, ItemWithSerializer.class);
        assertThat(readValue, notNullValue());
    }

    @Test
    public void whenDeserialisingZonedDateTimeWithDefaults_thenTimeZoneIsNotPreserved() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // construct a new instance of ZonedDateTime
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        String converted = objectMapper.writeValueAsString(now);
        // restore an instance of ZonedDateTime from String
        ZonedDateTime restored = objectMapper.readValue(converted, ZonedDateTime.class);
        System.out.println("serialized: " + now);
        System.out.println("restored: " + restored);
        assertThat(now, is(not(restored)));
    }

    @Test
    public void whenDeserialisingZonedDateTimeWithFeaturesDisabled_thenTimeZoneIsPreserved() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        // construct a new instance of ZonedDateTime
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        String converted = objectMapper.writeValueAsString(now);
        // restore an instance of ZonedDateTime from String
        ZonedDateTime restored = objectMapper.readValue(converted, ZonedDateTime.class);
        System.out.println("serialized: " + now);
        System.out.println("restored: " + restored);
        assertThat(now, is(restored));
    }

}

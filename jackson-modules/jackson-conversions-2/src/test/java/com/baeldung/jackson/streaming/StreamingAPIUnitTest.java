package com.baeldung.jackson.streaming;

import com.fasterxml.jackson.core.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class StreamingAPIUnitTest {

    @Test
    public void givenJsonGenerator_whenAppendJsonToIt_thenGenerateJson() throws IOException {
        // given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);

        // when
        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "Tom");
        jGenerator.writeNumberField("age", 25);
        jGenerator.writeFieldName("address");
        jGenerator.writeStartArray();
        jGenerator.writeString("Poland");
        jGenerator.writeString("5th avenue");
        jGenerator.writeEndArray();
        jGenerator.writeEndObject();
        jGenerator.close();

        // then
        String json = new String(stream.toByteArray(), "UTF-8");
        assertEquals(json, "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}");
    }

    @Test
    public void givenJson_whenReadItUsingStreamAPI_thenShouldCreateProperJsonObject() throws IOException {
        // given
        String json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new LinkedList<>();

        // when
        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jParser.nextToken();
                parsedName = jParser.getText();

            }

            if ("age".equals(fieldname)) {
                jParser.nextToken();
                parsedAge = jParser.getIntValue();

            }

            if ("address".equals(fieldname)) {
                jParser.nextToken();

                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    addresses.add(jParser.getText());
                }
            }

        }
        jParser.close();

        // then
        assertEquals(parsedName, "Tom");
        assertEquals(parsedAge, (Integer) 25);
        assertEquals(addresses, Arrays.asList("Poland", "5th avenue"));

    }

    @Test
    public void givenJson_whenWantToExtractPartOfIt_thenShouldExtractOnlyNeededFieldWithoutGoingThroughWholeJSON() throws IOException {
        // given
        String json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new LinkedList<>();

        // when
        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();

            if ("age".equals(fieldname)) {
                jParser.nextToken();
                parsedAge = jParser.getIntValue();
                return;
            }

        }
        jParser.close();

        // then
        assertNull(parsedName);
        assertEquals(parsedAge, (Integer) 25);
        assertTrue(addresses.isEmpty());

    }
}

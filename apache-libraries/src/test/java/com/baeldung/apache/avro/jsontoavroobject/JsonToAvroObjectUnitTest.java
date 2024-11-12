package com.baeldung.apache.avro.jsontoavroobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.Test;

public class JsonToAvroObjectUnitTest {

   final String json = """ 
            {"name":"John Doe","age":30,"email":{"string":"john@example.com"}}
            """ ;

    @Test
    public void whenValidJsonInput_thenConvertsToAvro() throws IOException {
        JsonToAvroConverter converter = new JsonToAvroConverter();

        GenericRecord record = converter.convertJsonToAvro(json);

        assertEquals("John Doe", record.get("name").toString());
        assertEquals(30, record.get("age"));
        assertEquals("john@example.com", record.get("email").toString());
    }

    @Test
    public void whenJsonWithNullableField_thenConvertsToAvro() throws IOException {
        JsonToAvroConverter converter = new JsonToAvroConverter();
        String json = """ 
            {"name":"John Doe","age":30,"email":null}
            """ ;
        GenericRecord record = converter.convertJsonToAvro(json);

        assertEquals("John Doe", record.get("name").toString());
        assertEquals(30, record.get("age"));
        assertNull(record.get("email"));
    }

    @Test
    public void whenJsonArray_thenConvertsToAvroList() throws IOException {
        JsonToAvroConverter converter = new JsonToAvroConverter();
        String jsonArray = """
        [
            {"name":"John Doe","age":30,"email":{"string":"john@example.com"}},
            {"name":"Jane Doe","age":28,"email":{"string":"jane@example.com"}}
        ]""";

        List<GenericRecord> records = converter.convertJsonArrayToAvro(jsonArray);

        assertEquals(2, records.size());
        assertEquals("John Doe", records.get(0).get("name").toString());
        assertEquals("jane@example.com", records.get(1).get("email").toString());
    }

    @Test
    public void whenSerializingAvroRecord_thenProducesByteArray() throws IOException {
        JsonToAvroConverter converter = new JsonToAvroConverter();
        GenericRecord record = converter.convertJsonToAvro(json);

        byte[] bytes = converter.serializeAvroRecord(record);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }
}

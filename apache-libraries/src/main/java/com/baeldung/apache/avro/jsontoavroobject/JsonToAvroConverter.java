package com.baeldung.apache.avro.jsontoavroobject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;

public class JsonToAvroConverter {

    private static final String SCHEMA_JSON = """
        {
            "type": "record",
            "name": "Customer",
            "namespace": "com.baeldung.avro",
            "fields": [
                {"name": "name", "type": "string"},
                {"name": "age", "type": "int"},
                {"name": "email", "type": ["null", "string"], "default": null}
            ]
        }""";

    private final Schema schema;

    public JsonToAvroConverter() {
        Schema.Parser parser = new Schema.Parser();
        schema = parser.parse(SCHEMA_JSON);
    }

    public GenericRecord convertJsonToAvro(String json) throws IOException {
        try {
            DatumReader<GenericRecord> reader = new GenericDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().jsonDecoder(schema, json);
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new IOException("Error converting JSON to Avro", e);
        }
    }

    public List<GenericRecord> convertJsonArrayToAvro(String jsonArray) throws IOException {
        List<GenericRecord> records = new ArrayList<>();

        // Create a schema for an array of our existing record schema
        Schema arraySchema = Schema.createArray(schema);

        // Use Avro's built-in JSON decoder
        Decoder decoder = DecoderFactory.get().jsonDecoder(arraySchema, jsonArray);
        DatumReader<List<GenericRecord>> reader = new GenericDatumReader<>(arraySchema);

        // Read the entire array at once
        List<GenericRecord> result = reader.read(null, decoder);
        return result;
    }

    public byte[] serializeAvroRecord(GenericRecord record) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

        writer.write(record, encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }
}

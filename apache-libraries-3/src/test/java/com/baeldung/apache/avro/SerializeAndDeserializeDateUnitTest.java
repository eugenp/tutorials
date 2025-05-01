package com.baeldung.apache.avro;

import org.apache.avro.Conversion;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.data.TimeConversions;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializeAndDeserializeDateUnitTest {

    @Test
    void whenSerializingDateWithLogicalType_thenDeserializesCorrectly() throws IOException {

        LocalDate expectedDate = LocalDate.now();
        Instant expectedTimestamp = Instant.now();

        byte[] serialized = serializeDateWithLogicalType(expectedDate, expectedTimestamp);
        Pair<LocalDate, Instant> deserialized = deserializeDateWithLogicalType(serialized);

        assertEquals(expectedDate, deserialized.getLeft());

        // This is perfectly valid when using logical types
        assertEquals(expectedTimestamp.toEpochMilli(), deserialized.getRight().toEpochMilli(),
                "Timestamps should match exactly at millisecond precision");
    }

    @Test
    void whenSerializingWithConversionApi_thenDeserializesCorrectly() throws IOException {

        LocalDate expectedDate = LocalDate.now();
        Instant expectedTimestamp = Instant.now();

        byte[] serialized = serializeWithConversionApi(expectedDate, expectedTimestamp);
        Pair<LocalDate, Instant> deserialized = deserializeWithConversionApi(serialized);

        assertEquals(expectedDate, deserialized.getLeft());
        assertEquals(expectedTimestamp.toEpochMilli(), deserialized.getRight().toEpochMilli(),
                "Timestamps should match at millisecond precision");
    }

    @Test
    void whenSerializingLegacyDate_thenConvertsCorrectly() throws IOException {

        Date legacyDate = new Date();
        LocalDate expectedLocalDate = legacyDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        byte[] serialized = serializeLegacyDateAsModern(legacyDate);
        LocalDate deserialized = deserializeDateWithLogicalType(serialized).getKey();

        assertEquals(expectedLocalDate, deserialized);
    }

    public static Schema createDateSchema() {
        String schemaJson =
                "{"
                        + "\"type\": \"record\","
                        + "\"name\": \"DateRecord\","
                        + "\"fields\": ["
                        + "  {\"name\": \"date\", \"type\": {\"type\": \"int\", \"logicalType\": \"date\"}},"
                        + "  {\"name\": \"timestamp\", \"type\": {\"type\": \"long\", \"logicalType\": \"timestamp-millis\"}}"
                        + "]"
                        + "}";
        return new Schema.Parser().parse(schemaJson);
    }

    public static byte[] serializeDateWithLogicalType(LocalDate date, Instant timestamp) throws IOException {
        Schema schema = createDateSchema();
        GenericRecord record = new GenericData.Record(schema);

        // Convert LocalDate to days since epoch
        record.put("date", (int) date.toEpochDay());

        // Convert Instant to milliseconds since epoch
        record.put("timestamp", timestamp.toEpochMilli());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(baos, null);

        datumWriter.write(record, encoder);
        encoder.flush();

        return baos.toByteArray();
    }

    public static Pair<LocalDate, Instant> deserializeDateWithLogicalType(byte[] bytes) throws IOException {
        Schema schema = createDateSchema();
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);

        GenericRecord record = datumReader.read(null, decoder);

        // Convert days since epoch back to LocalDate
        LocalDate date = LocalDate.ofEpochDay((int) record.get("date"));

        // Convert milliseconds since epoch back to Instant
        Instant timestamp = Instant.ofEpochMilli((long) record.get("timestamp"));

        return Pair.of(date, timestamp);
    }

    public static byte[] serializeWithConversionApi(LocalDate date, Instant timestamp) throws IOException {
        Schema schema = createDateSchema();
        GenericRecord record = new GenericData.Record(schema);

        // Use LogicalTypes.date() for conversion
        Conversion<LocalDate> dateConversion = new org.apache.avro.data.TimeConversions.DateConversion();
        LogicalTypes.date().addToSchema(schema.getField("date").schema());

        // Use LogicalTypes.timestampMillis() for conversion
        Conversion<Instant> timestampConversion = new org.apache.avro.data.TimeConversions.TimestampMillisConversion();
        LogicalTypes.timestampMillis().addToSchema(schema.getField("timestamp").schema());

        record.put("date", dateConversion.toInt(date, schema.getField("date").schema(), LogicalTypes.date()));
        record.put("timestamp", timestampConversion.toLong(timestamp, schema.getField("timestamp").schema(), LogicalTypes.timestampMillis()));

        // Serialize as before
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        Encoder encoder = EncoderFactory.get().binaryEncoder(baos, null);

        datumWriter.write(record, encoder);
        encoder.flush();

        return baos.toByteArray();
    }

    public static Pair<LocalDate, Instant> deserializeWithConversionApi(byte[] bytes) throws IOException {
        Schema schema = createDateSchema();
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);

        GenericRecord record = datumReader.read(null, decoder);

        // Use LogicalTypes.date() for conversion
        Conversion<LocalDate> dateConversion = new TimeConversions.DateConversion();
        LogicalTypes.date().addToSchema(schema.getField("date").schema());

        // Use LogicalTypes.timestampMillis() for conversion
        Conversion<Instant> timestampConversion = new TimeConversions.TimestampMillisConversion();
        LogicalTypes.timestampMillis().addToSchema(schema.getField("timestamp").schema());

        // Get the primitive values from the record
        int daysSinceEpoch = (int) record.get("date");
        long millisSinceEpoch = (long) record.get("timestamp");

        // Convert back to Java types using the conversion API
        LocalDate date = dateConversion.fromInt(
                daysSinceEpoch,
                schema.getField("date").schema(),
                LogicalTypes.date()
        );

        Instant timestamp = timestampConversion.fromLong(
                millisSinceEpoch,
                schema.getField("timestamp").schema(),
                LogicalTypes.timestampMillis()
        );

        return Pair.of(date, timestamp);
    }

    public static byte[] serializeLegacyDateAsModern(Date legacyDate) throws IOException {
        // Convert java.util.Date to java.time.Instant
        Instant instant = legacyDate.toInstant();

        // Convert to LocalDate if you need date-only information
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // Then use one of our modern date serialization methods
        return serializeDateWithLogicalType(localDate, instant);
    }
}

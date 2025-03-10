package com.baeldung.apache.avro.storingnullvaluesinavrofile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AvroUserUnitTest {

    private AvroUser user;
    private Schema schema;

    @BeforeEach
    void setUp() {
        user = new AvroUser();
        user.setId(1998983092L);
        user.setName("Test Name 1");
        user.setActive(true);  // Using @Nullable
        user.setLastUpdatedBy("yesterday");  // Using union type
        user.setEmail("emailAddress");  // Using optional field
    }

    @Test
    void whenSerializingUserWithNullPropFromStringSchema_thenSuccess(@TempDir Path tempDir) {
        user.setLastUpdatedBy(null);
        schema = AvroUser.createSchemaWithOptionalFields1();

        String filePath = tempDir.resolve("test.avro").toString();
        GenericRecord record = AvroUser.createRecord(AvroUser.createSchemaFromJson(), user);

        assertDoesNotThrow(() -> AvroUser.writeToAvroFile(schema, record, filePath));

        File avroFile = new File(filePath);
        assertTrue(avroFile.exists());
        assertTrue(avroFile.length() > 0);
    }

    @Test
    void givenSchemaBuilderWithOptionalFields1_whenCreatingSchema_thenSupportsNull(@TempDir Path tempDir) {
        user.setLastUpdatedBy(null);
        String filePath = tempDir.resolve("test.avro").toString();

        schema = AvroUser.createSchemaWithOptionalFields1();
        GenericRecord record = AvroUser.createRecord(schema, user);

        assertTrue(schema.getField("lastUpdatedBy").schema().isNullable(),
            "Union type field should be nullable");
        assertDoesNotThrow(() -> AvroUser.writeToAvroFile(schema, record, filePath));

        File avroFile = new File(filePath);
        assertTrue(avroFile.exists());
        assertTrue(avroFile.length() > 0);
    }

    @Test
    void givenSchemaBuilderWithOptionalFields2_whenCreatingSchema_thenSupportsNull(@TempDir Path tempDir) {
        user.setEmail(null);
        String filePath = tempDir.resolve("test.avro").toString();

        schema = AvroUser.createSchemaWithOptionalFields2();
        GenericRecord record = AvroUser.createRecord(schema, user);

        assertTrue(schema.getField("email").schema().isNullable(),
            "Union type field should be nullable");
        assertDoesNotThrow(() -> AvroUser.writeToAvroFile(schema, record, filePath));

        File avroFile = new File(filePath);
        assertTrue(avroFile.exists());
        assertTrue(avroFile.length() > 0);
    }
}

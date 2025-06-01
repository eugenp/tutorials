package com.baeldung.apache.avro.storingnullvaluesinavrofile;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.Nullable;

public class AvroUser {
    private long id;
    private String name;
    @Nullable
    private Boolean active;  // Example for @Nullable annotation approach
    private String lastUpdatedBy;  // Example for union type approach
    private String email;  // Example for optional field approach

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Schema definition approach 1: using JSON string with union types
    private static final String SCHEMA_JSON = """
        {
            "type": "record",
            "name": "User",
            "namespace": "com.baeldung.avro",
            "fields": [
                {"name": "id", "type": "long"},
                {"name": "name", "type": "string"},
                {"name": "active", "type": "boolean"},
                {"name": "lastUpdatedBy", "type": ["null", "string"], "default": null},
                {"name": "email", "type": "string"}
            ]
        }""";

    public static Schema createSchemaFromJson() {
        return new Schema.Parser().parse(SCHEMA_JSON);
    }

    // Schema definition approach 2: using SchemaBuilder with union type methods
    public static Schema createSchemaWithOptionalFields1() {
        return SchemaBuilder
            .record("User")
            .namespace("com.baeldung.apache.avro.storingnullvaluesinavrofile")
            .fields()
            .requiredLong("id")
            .requiredString("name")
            .requiredBoolean("active")
            .name("lastUpdatedBy")
            .type()
            .unionOf()
            .nullType()
            .and()
            .stringType()
            .endUnion()
            .nullDefault()
            .requiredString("email")
            .endRecord();
    }

    // Schema definition approach 3: using SchemaBuilder with optional fields
    public static Schema createSchemaWithOptionalFields2() {
        return SchemaBuilder
            .record("User")
            .namespace("com.baeldung.apache.avro.storingnullvaluesinavrofile")
            .fields()
            .requiredLong("id")
            .requiredString("name")
            .requiredBoolean("active")  // Using @Nullable
            .requiredString("lastUpdatedBy")  // Using union type in second approach
            .optionalString("email")  // Using optional field
            .endRecord();
    }

    public static void writeToAvroFile(Schema schema, GenericRecord record, String filePath) throws IOException {
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(schema, new File(filePath));
            dataFileWriter.append(record);
        }
    }

    public static GenericRecord createRecord(Schema schema, AvroUser user) {
        GenericRecord record = new GenericData.Record(schema);
        record.put("id", user.getId());
        record.put("name", user.getName());
        record.put("active", user.getActive());
        record.put("lastUpdatedBy", user.getLastUpdatedBy());
        record.put("email", user.getEmail());
        return record;
    }
}
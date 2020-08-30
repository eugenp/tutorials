package com.baeldung.avro.util;


import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class AvroSchemaBuilder {

    public Schema createAvroHttpRequestSchema(){

        Schema clientIdentifier = SchemaBuilder.record("ClientIdentifier").namespace("com.baeldung.avro.model")
                .fields().requiredString("hostName").requiredString("ipAddress").endRecord();

        Schema avroHttpRequest = SchemaBuilder.record("AvroHttpRequest").namespace("com.baeldung.avro.model").fields()
                .requiredLong("requestTime")
                .name("clientIdentifier").type(clientIdentifier).noDefault()
                .name("employeeNames").type().array().items().stringType().arrayDefault(null)
                .name("active").type().enumeration("Active").symbols("YES", "NO").noDefault()
                .endRecord();
        return avroHttpRequest;
    }
}



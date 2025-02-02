package com.baeldung.apache.avro.util;

import static java.util.Collections.emptyList;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class AvroSchemaBuilder {

    //@formatter:off
    static Schema clientIdentifierSchema() {
        return SchemaBuilder.record("ClientIdentifier")
          .namespace("com.baeldung.avro.model")
          .fields()
          .requiredString("hostName")
          .requiredString("ipAddress")
          .endRecord();
    }

    static Schema avroHttpRequestSchema() {
        return SchemaBuilder.record("AvroHttpRequest")
          .namespace("com.baeldung.avro.model").fields()
          .requiredLong("requestTime")
          .name("clientIdentifier")
            .type(clientIdentifierSchema())
            .noDefault()
          .name("employeeNames")
            .type()
            .array()
            .items()
            .stringType()
            .arrayDefault(emptyList())
          .name("active")
            .type()
            .enumeration("Active")
            .symbols("YES", "NO")
            .noDefault()
          .endRecord();
    }
    //@formatter:on
}



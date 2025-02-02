package com.baeldung.apache.avro.util;

import static com.baeldung.apache.avro.util.AvroSchemaBuilder.clientIdentifierSchema;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.apache.avro.Schema;
import org.junit.jupiter.api.Test;

class AvroSchemaBuilderUnitTest {

    @Test
    void whenCallingSchemaToString_thenReturnJsonAvroSchema() {
        Schema clientIdSchema = clientIdentifierSchema();

        assertThatJson(clientIdSchema.toString()).isEqualTo("""
            {
               "type":"record",
               "name":"ClientIdentifier",
               "namespace":"com.baeldung.avro.model",
               "fields":[
                  {
                     "name":"hostName",
                     "type":"string"
                  },
                  {
                     "name":"ipAddress",
                     "type":"string"
                  }
               ]
            }
            """);
    }

}
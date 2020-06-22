package com.baeldung.jsonld.serialization.jacksonjsonld;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ioinformarics.oss.jackson.module.jsonld.HydraCollection;
import ioinformarics.oss.jackson.module.jsonld.JsonldGraph;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;

public class JacksonJsonLdSerializationExample {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonldModule());

        JacksonJsonldExamplePerson person = new JacksonJsonldExamplePerson();

        JsonldResource jsonldResource = JsonldResource.Builder.create()
            .build(person);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(jsonldResource));
    }
}

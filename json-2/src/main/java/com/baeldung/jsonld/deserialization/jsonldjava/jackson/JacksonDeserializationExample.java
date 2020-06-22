package com.baeldung.jsonld.deserialization.jsonldjava.jackson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

public class JacksonDeserializationExample {
    public static void main(String[] args) throws IOException {
        String exampleJsonld ="{" + 
           "  \"@context\": {" + 
           "    \"@vocab\": \"http://schema.org/\"," + 
           "    \"knows\": {" + 
           "      \"@type\": \"@id\"" + 
           "    }" + 
           "  }," + 
           "  \"@type\": \"Person\"," + 
           "  \"@id\": \"http://example.com/person/1234\"," + 
           "  \"name\": \"Example Name\"," + 
           "  \"knows\": \"http://example.com/person/2345\"" + 
           "}";

        Object jsonObject = JsonUtils.fromString(exampleJsonld);
        Object compact = JsonLdProcessor.compact(jsonObject, new HashMap(), new JsonLdOptions());
        String compactContent = JsonUtils.toString(compact);

        System.out.println(JsonUtils.toPrettyString(compact));

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonExamplePerson person = objectMapper.readValue(compactContent, JacksonExamplePerson.class);

        System.out.println(person.id);
        System.out.println(person.name);
        System.out.println(person.knows.id);
    }
}

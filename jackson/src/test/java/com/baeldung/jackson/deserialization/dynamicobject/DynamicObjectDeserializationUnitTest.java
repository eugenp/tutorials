package com.baeldung.jackson.deserialization.dynamicobject;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicObjectDeserializationUnitTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    private String readResource(String path) {
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(path), "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    @Test
    void givenJsonString_whenParsingToJsonNode_thenItMustContainDynamicProperties() throws JsonParseException, JsonMappingException, IOException {
        // given
        String json = readResource("/deserialize-dynamic-object/embedded.json");

        // when
        ProductJsonNode product = objectMapper.readValue(json, ProductJsonNode.class);

        // then
        assertThat(product.getName()).isEqualTo("Pear yPhone 72");
        assertThat(product.getDetails().get("audioConnector").asText()).isEqualTo("none");
    }

    @Test
    void givenJsonString_whenParsingToMap_thenItMustContainDynamicProperties() throws JsonParseException, JsonMappingException, IOException {
        // given
        String json = readResource("/deserialize-dynamic-object/embedded.json");

        // when
        ProductMap product = objectMapper.readValue(json, ProductMap.class);

        // then
        assertThat(product.getName()).isEqualTo("Pear yPhone 72");
        assertThat(product.getDetails().get("audioConnector")).isEqualTo("none");
    }

    @Test
    void givenJsonString_whenParsingWithJsonAnySetter_thenItMustContainDynamicProperties() throws JsonParseException, JsonMappingException, IOException {
        // given
        String json = readResource("/deserialize-dynamic-object/flat.json");

        // when
        Product product = objectMapper.readValue(json, Product.class);

        // then
        assertThat(product.getName()).isEqualTo("Pear yPhone 72");
        assertThat(product.getDetails().get("audioConnector")).isEqualTo("none");
    }

}

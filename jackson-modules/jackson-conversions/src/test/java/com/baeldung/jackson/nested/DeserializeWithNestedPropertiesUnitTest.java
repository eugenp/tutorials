package com.baeldung.jackson.nested;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class DeserializeWithNestedPropertiesUnitTest {

    private String SOURCE_JSON = "{\"id\":\"957c43f2-fa2e-42f9-bf75-6e3d5bb6960a\",\"name\":\"The Best Product\",\"brand\":{\"id\":\"9bcd817d-0141-42e6-8f04-e5aaab0980b6\",\"name\":\"ACME Products\",\"owner\":{\"id\":\"b21a80b1-0c09-4be3-9ebd-ea3653511c13\",\"name\":\"Ultimate Corp, Inc.\"}}}";

    @Test
    public void whenUsingAnnotations_thenOk() throws IOException {
        Product product = new ObjectMapper().readerFor(Product.class)
            .readValue(SOURCE_JSON);

        assertEquals(product.getName(), "The Best Product");
        assertEquals(product.getBrandName(), "ACME Products");
        assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
    }

    @Test
    public void whenUsingJsonNode_thenOk() throws IOException {
        JsonNode productNode = new ObjectMapper().readTree(SOURCE_JSON);

        Product product = new Product();
        product.setId(productNode.get("id")
            .textValue());
        product.setName(productNode.get("name")
            .textValue());
        product.setBrandName(productNode.get("brand")
            .get("name")
            .textValue());
        product.setOwnerName(productNode.get("brand")
            .get("owner")
            .get("name")
            .textValue());

        assertEquals(product.getName(), "The Best Product");
        assertEquals(product.getBrandName(), "ACME Products");
        assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
    }

    @Test
    public void whenUsingDeserializerManuallyRegistered_thenOk() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        mapper.registerModule(module);

        Product product = mapper.readValue(SOURCE_JSON, Product.class);
        assertEquals(product.getName(), "The Best Product");
        assertEquals(product.getBrandName(), "ACME Products");
        assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
    }

    @Test
    public void whenUsingDeserializerAutoRegistered_thenOk() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(SOURCE_JSON, Product.class);
        assertEquals(product.getName(), "The Best Product");
        assertEquals(product.getBrandName(), "ACME Products");
        assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
    }
}

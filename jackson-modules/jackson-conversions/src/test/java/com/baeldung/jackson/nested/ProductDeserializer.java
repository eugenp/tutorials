package com.baeldung.jackson.nested;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class ProductDeserializer extends StdDeserializer<Product> {

    public ProductDeserializer() {
        this(null);
    }

    public ProductDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Product deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode productNode = jp.getCodec()
            .readTree(jp);
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
        return product;
    }
}

package com.baeldung.jackson.pojomapping;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BsonProductMapperUnitTest {

    private BsonProductMapper mapper;
    private Product product;

    @BeforeEach
    void setUp() {
        mapper = new BsonProductMapper();
        product = new Product("Test Product", 29.99);
    }

    @Test
    void whenSerializingProduct_thenReturnsByteArray() throws IOException {
        byte[] bytes = mapper.toBytes(product);

        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }

    @Test
    void givenSerializedProduct_whenDeserializing_thenReturnsProduct() throws IOException {
        byte[] bytes = mapper.toBytes(product);

        Product deserializedProduct = mapper.fromBytes(bytes);

        assertEquals(product.getName(), deserializedProduct.getName());
        assertEquals(product.getPrice(), deserializedProduct.getPrice(), 0.01);
    }

    @Test
    void whenConvertingProductToDocument_thenReturnsValidDocument() throws IOException {
        Document document = mapper.toDocument(product);

        assertNotNull(document);
        assertEquals(product.getName(), document.getString("name"));
        assertEquals(product.getPrice(), document.getDouble("price"), 0.01);
    }

        @Test
    void whenRoundTrippingProduct_thenDataIsPreserved() throws IOException {
        Document document = mapper.toDocument(product);
        Product roundTrippedProduct = mapper.fromDocument(document);

        assertEquals(product.getName(), roundTrippedProduct.getName());
        assertEquals(product.getPrice(), roundTrippedProduct.getPrice(), 0.01);
    }

    @Test
    void givenDocument_whenConvertingToProduct_thenReturnsProduct() throws IOException {
        Document document = new Document()
          .append("name", "Document Product")
          .append("price", 49.99);

        Product convertedProduct = mapper.fromDocument(document);

        assertEquals("Document Product", convertedProduct.getName());
        assertEquals(49.99, convertedProduct.getPrice(), 0.01);
    }

    @Test
    void givenProductWithNullFields_whenSerializing_thenHandlesGracefully() throws IOException {

        Product productWithNulls = new Product();
        productWithNulls.setPrice(10.0);

        byte[] bytes = mapper.toBytes(productWithNulls);
        Product deserializedProduct = mapper.fromBytes(bytes);

        assertNull(deserializedProduct.getName());
        assertEquals(10.0, deserializedProduct.getPrice(), 0.01);
    }
}

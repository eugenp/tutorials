package com.baeldung.jersey.response;

import com.baeldung.jersey.model.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("XMLResponse Tests")
class XMLResponseUnitTest {

    @Mock private Client client;
    @Mock private WebTarget webTarget;
    @Mock private Invocation.Builder builder;
    @Mock private Response response;
    @Mock private Logger logger;

    private XMLResponse xmlResponse;
    private static final String BASE_URL = "https://api.example.com/product";

    @BeforeEach
    void setUp() {
        xmlResponse = new XMLResponse(client, logger, BASE_URL);

        // Common mock setup
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_XML)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(response);
    }

    @Test
    @DisplayName("Should successfully fetch product data")
    void whenValidProductId_thenReturnProductData() {
        // Given
        String validXml = "<product><id>1</id><name>Test Product</name></product>";
        when(response.getStatus()).thenReturn(Response.Status.OK.getStatusCode());
        when(response.readEntity(InputStream.class))
                .thenReturn(new ByteArrayInputStream(validXml.getBytes()));

        // When
        Product product = xmlResponse.fetchProductData(1);

        // Then
        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        verify(response).close();
    }

    @Test
    @DisplayName("Should handle non-200 response")
    void whenNon200Response_thenReturnNull() {
        // Given
        when(response.getStatus()).thenReturn(Response.Status.BAD_REQUEST.getStatusCode());

        // When
        Product product = xmlResponse.fetchProductData(1);

        // Then
        assertNull(product);
        verify(logger).error(eq("Failed to get product data. Status: {}"), eq(400));
        verify(response).close();
    }
}
package com.baeldung.jersey.server.response;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class XMLResponseTest {

    private WireMockServer wireMockServer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        System.setProperty("api.base.url", "http://localhost:8089");
    }

    @After
    public void teardown() {
        wireMockServer.stop();
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.clearProperty("api.base.url");
    }

    @Test
    public void whenProductExists_thenReturnProductName() throws JAXBException {
        // Given
        String successResponse = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<product>" +
            "    <id>1</id>" +
            "    <name>Test Product</name>" +
            "</product>";

        stubFor(post(urlEqualTo("/product"))
            .withRequestBody(equalToXml("<product><id>1</id></product>"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_XML)
                .withBody(successResponse)));

        // When
        XMLResponse.main(new String[]{});

        // Then
        assertThat(outContent.toString().trim())
            .contains("Product Name: Test Product");
        
        verify(1, postRequestedFor(urlEqualTo("/product"))
            .withHeader("Content-Type", containing(MediaType.APPLICATION_XML)));
    }

    @Test
    public void whenProductRequestFails_thenShowError() throws JAXBException {
        // Given
        stubFor(post(urlEqualTo("/product"))
            .withRequestBody(equalToXml("<product><id>1</id></product>"))
            .willReturn(aResponse()
                .withStatus(404)));

        // When
        XMLResponse.main(new String[]{});

        // Then
        assertThat(errContent.toString().trim())
            .contains("Failed to get product data");
        
        verify(1, postRequestedFor(urlEqualTo("/product")));
    }

    @Test
    public void whenMalformedXMLResponse_thenThrowException() {
        // Given
        String malformedResponse = "<invalid>xml</invalid";
        stubFor(post(urlEqualTo("/product"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_XML)
                .withBody(malformedResponse)));

        // When & Then
        assertThrows(JAXBException.class, () -> {
            XMLResponse.main(new String[]{});
        });
    }

    @Test
    public void whenServerTimeout_thenHandleGracefully() {
        // Given
        stubFor(post(urlEqualTo("/product"))
            .willReturn(aResponse()
                .withFixedDelay(5000))); // 5 second delay

        // When & Then
        assertThrows(ProcessingException.class, () -> {
            XMLResponse.main(new String[]{});
        });
    }

    @Test
    public void whenInvalidContentType_thenHandleGracefully() throws JAXBException {
        // Given
        stubFor(post(urlEqualTo("/product"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody("{\"invalid\":\"response\"}")));

        // When
        XMLResponse.main(new String[]{});

        // Then
        assertThat(errContent.toString().trim())
            .contains("Failed to get product data");
    }
}
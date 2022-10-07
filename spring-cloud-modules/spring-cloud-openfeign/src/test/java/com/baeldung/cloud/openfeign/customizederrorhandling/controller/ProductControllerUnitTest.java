package com.baeldung.cloud.openfeign.customizederrorhandling.controller;

import com.baeldung.cloud.openfeign.customizederrorhandling.client.ProductClient;
import com.baeldung.cloud.openfeign.customizederrorhandling.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ProductControllerUnitTest {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WireMockServer wireMockServer;

    @Before
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8081);
        configureFor("localhost", 8081);
        wireMockServer.start();
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_ThenReturnInternalServerError() throws Exception {
        String productId = "test";

        stubFor(WireMock.get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse()
            .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        ErrorResponse expectedError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "Product Api is unavailable","uri=/myapp2/product/" + productId);

        MvcResult result = mockMvc.perform(get("/myapp2/product/" + productId))
            .andExpect(status().isInternalServerError()).andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(expectedError.getCode(), errorResponse.getCode());
        assertEquals(expectedError.getMessage(), errorResponse.getMessage());
        assertEquals(expectedError.getStatus(), errorResponse.getStatus());
        assertEquals(expectedError.getDetails(), errorResponse.getDetails());
    }

    @Test
    public void givenProductIsNotFound_whenGetProductCalled_ThenReturnInternalServerError() throws Exception {
        String productId = "test";

        stubFor(WireMock.get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse()
            .withStatus(HttpStatus.NOT_FOUND.value())));

        ErrorResponse expectedError = new ErrorResponse(HttpStatus.NOT_FOUND,
          "Product not found","uri=/myapp2/product/" + productId);

        MvcResult result = mockMvc.perform(get("/myapp2/product/" + productId))
            .andExpect(status().isNotFound()).andReturn();

        ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals(expectedError.getCode(), errorResponse.getCode());
        assertEquals(expectedError.getMessage(), errorResponse.getMessage());
        assertEquals(expectedError.getStatus(), errorResponse.getStatus());
        assertEquals(expectedError.getDetails(), errorResponse.getDetails());
    }
}

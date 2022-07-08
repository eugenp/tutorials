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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ProductControllerTest {

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

    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_ThenReturnInternalServerError() throws Exception {
        String productId = "test";

        WireMock.stubFor(WireMock.get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        ErrorResponse expectedError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
        "Product Api is unavailable","uri=/myapp2/product/" + productId);

        mockMvc.perform(get("/myapp2/product/" + productId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
    }

    @Test
    public void givenProductIsNotFound_whenGetProductCalled_ThenReturnInternalServerError() throws Exception {
        String productId = "test";

        WireMock.stubFor(WireMock.get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        ErrorResponse expectedError = new ErrorResponse(HttpStatus.NOT_FOUND,
                "Product not found","uri=/myapp2/product/" + productId);

        mockMvc.perform(get("/myapp2/product/" + productId))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}

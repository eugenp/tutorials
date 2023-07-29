package com.baeldung.cloud.openfeign.defaulterrorhandling.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.baeldung.cloud.openfeign.defaulterrorhandling.client.ProductClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class, TestControllerAdvice.class})
@EnableWebMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @Before
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenProductServiceIsnotAvailable_whenGetProductCalled_thenReturnInternalServerError() throws Exception {
        String productId = "test";

        stubFor(WireMock.get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse().withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        mockMvc.perform(get("/myapp1/product/" + productId))
            .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    @Test
    public void givenProductIsNotFound_whenGetProductCalled_thenReturnBadeRequestError() throws Exception {
        String productId = "test";

        stubFor(WireMock.get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        mockMvc.perform(get("/myapp1/product/" +productId))
            .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}

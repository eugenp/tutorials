package com.baeldung.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerContentNegotiationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenEndpointUsingJsonParameterCalled_thenJsonResponseObtained() throws Exception {
        this.mockMvc.perform(get("/employee/1?mediaType=json"))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void whenEndpointUsingXmlParameterCalled_thenXmlResponseObtained() throws Exception {
        this.mockMvc.perform(get("/employee/1?mediaType=xml"))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE));
    }

    @Test
    public void whenEndpointUsingJsonAcceptHeaderCalled_thenJsonResponseObtained() throws Exception {
        this.mockMvc.perform(get("/employee/1").header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void whenEndpointUsingXmlAcceptHeaderCalled_thenXmlResponseObtained() throws Exception {
        this.mockMvc.perform(get("/employee/1").header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE));
    }
}

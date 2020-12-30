package com.baeldung.web.controller.mediatypes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.sampleapp.config.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class CustomMediaTypeControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenServiceUrl_whenGetWithProperAcceptHeaderFirstAPIVersion_thenReturn200() throws Exception {
        mockMvc.perform(get("/public/api/items/1").accept("application/vnd.baeldung.api.v1+json")).andExpect(status().isOk());
    }

    @Test
    public void givenServiceUrl_whenGetWithProperAcceptHeaderSecondVersion_thenReturn200() throws Exception {
        mockMvc.perform(get("/public/api/items/2").accept("application/vnd.baeldung.api.v2+json")).andExpect(status().isOk());
    }
}
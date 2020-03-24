package com.baeldung.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.sampleapp.config.WebConfig;
import com.baeldung.sampleapp.web.dto.HeavyResource;
import com.baeldung.sampleapp.web.dto.HeavyResourceAddressOnly;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class HeavyResourceControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenHeavyResource_whenSendPutRequest_thenCreateResource() throws Exception {
        mockMvc.perform(put("/heavyresource/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new HeavyResource(1, "Tom", "Jackson", 12, "heaven street")))
        ).andExpect(status().isOk());
    }

    @Test
    public void givenNewAddressOfResource_whenExecutePatchRequest_thenUpdateResourcePartially() throws Exception {
        mockMvc.perform(patch("/heavyresource/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new HeavyResourceAddressOnly(1, "5th avenue")))
        ).andExpect(status().isOk());
    }

    @Test
    public void givenNewAddressOfResource_whenExecutePatchGeneric_thenUpdateResourcePartially() throws Exception {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("address", "5th avenue");

        mockMvc.perform(patch("/heavyresource/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updates))
        ).andExpect(status().isOk());
    }

}
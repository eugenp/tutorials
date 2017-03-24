package org.baeldung.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.baeldung.config.WebConfig;
import org.baeldung.web.dto.HeavyResource;
import org.baeldung.web.dto.HeavyResourceAddressOnly;
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

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class HeavyResourceControllerTest {

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
        mockMvc.perform(put("/heavy")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new HeavyResource(1, "Tom", "Jackson", 12, "heaven street")))
        ).andExpect(status().isOk());
    }

    @Test
    public void givenNewAddressOfResource_whenExecutePatchRequest_thenUpdateResourcePartially() throws Exception {
        mockMvc.perform(patch("/heavy")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new HeavyResourceAddressOnly(1, "5th avenue")))
        ).andExpect(status().isOk());
    }

    @Test
    public void givenNewAddressOfResource_whenExecutePatchGeneric_thenUpdateResourcePartially() throws Exception {
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("address", "5th avenue");

        mockMvc.perform(patch("/heavy/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updates))
        ).andExpect(status().isOk());
    }

}
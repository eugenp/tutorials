package com.baeldung.springdatarestquerydsl;

import com.baeldung.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class) @SpringBootTest(classes = Application.class) @WebAppConfiguration
public class QuerydslIntegrationTest {

    final MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test public void givenRequestHasBeenMade_whenQueryAddressFilteringByUserName_thenGetJohn() throws Exception {
        mockMvc.perform(get("/addresses?user.name=John")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].address", is("Fake Street 1")))
                .andExpect(jsonPath("$[0].country", is("Fake Country")));
    }

    @Test public void givenRequestHasBeenMade_whenQueryOverAddressAvailabilityFilteringByAddressCountry_thenGetAvailability()
            throws Exception {
        mockMvc.perform(get("/addressAvailabilities?address.country=Real Country")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].monday", is(false))).andExpect(jsonPath("$[0].tuesday", is(false)))
                .andExpect(jsonPath("$[0].wednesday", is(false))).andExpect(jsonPath("$[0].thursday", is(false)))
                .andExpect(jsonPath("$[0].friday", is(false))).andExpect(jsonPath("$[0].saturday", is(true)))
                .andExpect(jsonPath("$[0].sunday", is(true)));
    }
}

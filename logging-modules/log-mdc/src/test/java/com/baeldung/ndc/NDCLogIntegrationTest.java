package com.baeldung.ndc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.baeldung.config.AppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
@WebAppConfiguration
public class NDCLogIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Investment investment;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        investment = new Investment();
        investment.setTransactionId("123");
        investment.setOwner("Mark");
        investment.setAmount(1000L);
    }

    @Test
    public void givenLog4jLogger_whenNDCAdded_thenResponseOkAndNDCInLog() throws Exception {
        mockMvc.perform(post("/ndc/log4j", investment).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investment))).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void givenLog4j2Logger_whenNDCAdded_thenResponseOkAndNDCInLog() throws Exception {
        mockMvc.perform(post("/ndc/log4j2", investment).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investment))).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void givenJBossLoggerBridge_whenNDCAdded_thenResponseOkAndNDCInLog() throws Exception {
        mockMvc.perform(post("/ndc/jboss-logging", investment).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(investment))).andExpect(status().is2xxSuccessful());

    }

}

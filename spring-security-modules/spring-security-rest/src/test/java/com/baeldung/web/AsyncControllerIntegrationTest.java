package com.baeldung.web;

import com.baeldung.security.SecurityJavaConfig;
import com.baeldung.spring.ClientWebConfig;
import com.baeldung.spring.WebConfig;
import com.baeldung.web.controller.AsyncController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ClientWebConfig.class, SecurityJavaConfig.class, WebConfig.class })
public class AsyncControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpSession session;

    @Mock
    AsyncController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testAsync() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/async")).andExpect(status().is5xxServerError());
    }

}

package com.baeldung.springbootadminclient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SpringBootAdminClientApplicationIntegrationTest {

    @Autowired Environment environment;

    @Autowired WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
          .webAppContextSetup(wac)
          .build();
    }

    @Test
    public void whenEnvironmentAvailable_ThenAdminServerPropertiesExist() {
        assertEquals(environment.getProperty("spring.boot.admin.client.url"), "http://localhost:8080");
        assertEquals(environment.getProperty("spring.boot.admin.client.username"), "admin");
        assertEquals(environment.getProperty("spring.boot.admin.client.password"), "admin");
    }

    @Test
    public void whenHttpBasicAttempted_ThenSuccess() throws Exception {
        mockMvc.perform(get("/actuator/env").with(httpBasic("client", "client")));
    }

    @Test
    public void whenInvalidHttpBasicAttempted_ThenUnauthorized() throws Exception {
        mockMvc
          .perform(get("/actuator/env").with(httpBasic("client", "invalid")))
          .andExpect(unauthenticated());
    }
}

package com.baeldung.overridebean.profile;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.overridebean.Endpoint;
import com.baeldung.overridebean.boot.Application;

@SpringBootTest(classes = { Application.class, ProfileConfig.class, Endpoint.class, ProfileTestConfig.class })
@AutoConfigureMockMvc
@ActiveProfiles("stub")
class ProfileStubIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenConfigurationWithProfile_whenTestProfileIsActive_thenStubOk() throws Exception {
        this.mockMvc.perform(get("/hello"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("hello profile stub")));
    }
}

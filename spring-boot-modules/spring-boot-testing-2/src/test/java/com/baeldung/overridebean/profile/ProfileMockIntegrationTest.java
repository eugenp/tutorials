package com.baeldung.overridebean.profile;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
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
import com.baeldung.overridebean.Service;
import com.baeldung.overridebean.boot.Application;

@SpringBootTest(classes = { Application.class, ProfileConfig.class, Endpoint.class, ProfileTestConfig.class })
@AutoConfigureMockMvc
@ActiveProfiles("mock")
class ProfileMockIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Service service;

    @Test
    void givenConfigurationWithProfile_whenTestProfileIsActive_thenMockOk() throws Exception {
        when(service.helloWorld()).thenReturn("hello profile mock");
        this.mockMvc.perform(get("/hello"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("hello profile mock")));
    }
}

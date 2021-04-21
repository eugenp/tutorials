package com.baeldung.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "management.health.random.enabled=false")
class DisabledRandomHealthIndicatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenADisabledIndicator_whenSendingRequest_thenReturns404() throws Exception {
        mockMvc.perform(get("/actuator/health/random"))
          .andExpect(status().isNotFound());
    }
}

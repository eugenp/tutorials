package com.baeldung.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RandomHealthIndicatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenRandomIndicator_whenCallingTheAPI_thenReturnsExpectedDetails() throws Exception {
        mockMvc.perform(get("/actuator/health/random"))
          .andExpect(jsonPath("$.status").exists())
          .andExpect(jsonPath("$.details.strategy").value("thread-local"))
          .andExpect(jsonPath("$.details.chance").exists());
    }
}
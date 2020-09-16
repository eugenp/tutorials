package com.baeldung.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WarningHealthIndicatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenCustomMapping_whenCallingTheAPI_thenTheStatusCodeIsAsExpected() throws Exception {
        mockMvc.perform(get("/actuator/health/warning"))
          .andExpect(jsonPath("$.status").value("WARNING"))
          .andExpect(status().isInternalServerError());
    }
}
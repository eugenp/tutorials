package com.baeldung.endpoints.enabling;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointEnablingIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void givenWrongAuthentication_whenCallingActuator_thenReturns401() throws Exception {
        mockMvc.perform(get("/actuator"))
          .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void givenProperAuthentication_whenCallingActuator_thenReturnsExpectedEndpoints() throws Exception {
        mockMvc.perform(get("/actuator"))
          .andExpect(jsonPath("$._links").exists())
          .andExpect(jsonPath("$._links.beans").exists())
          .andExpect(jsonPath("$._links.env").exists())
          .andExpect(jsonPath("$._links.shutdown").exists());
    }
}

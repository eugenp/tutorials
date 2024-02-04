package com.baeldung.queryparamdoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class BookControllerMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void smokeTest() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void giveEndpoint_whenSendGetRequest_thenSuccessfulResponse() throws Exception {
        mockMvc.perform(get("/books"))
          .andExpect(status().isOk());
    }
}
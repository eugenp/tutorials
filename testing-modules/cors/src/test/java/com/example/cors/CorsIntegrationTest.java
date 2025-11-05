package com.example.cors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureMockMvc
class CorsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @SneakyThrows
    void whenRequestFromAllowedOrigin_thenAllowsCors() {
        mockMvc.perform(get("/api/v1/joke")
          .header("Origin", "https://baeldung.com"))
          .andExpect(status().isOk())
          .andExpect(header().string("Access-Control-Allow-Origin", "https://baeldung.com"));
    }
    
    @Test
    @SneakyThrows
    void whenRequestFromNotAllowedOrigin_thenDoesNotAllowCors() {
        mockMvc.perform(get("/api/v1/joke")
          .header("Origin", "https://non-baeldung.com"))
          .andExpect(status().isForbidden())
          .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
    
    @Test
    @SneakyThrows
    void whenPreflightRequestWithAllowedMethod_thenAllowsCors() {
        mockMvc.perform(options("/api/v1/joke")
          .header("Origin", "https://baeldung.com")
          .header("Access-Control-Request-Method", "GET"))
          .andExpect(status().isOk())
          .andExpect(header().string("Access-Control-Allow-Methods", "GET"));
    }
    
    @Test
    @SneakyThrows
    void whenPreflightRequestWithNotAllowedMethod_thenDoesNotAllowCors() {
        mockMvc.perform(options("/api/v1/joke")
          .header("Origin", "https://baeldung.com")
          .header("Access-Control-Request-Method", "POST"))
          .andExpect(status().isForbidden());
    }
    
    @Test
    @SneakyThrows
    void whenPreflightRequestWithAllowedHeader_thenAllowsCors() {
        mockMvc.perform(options("/api/v1/joke")
          .header("Origin", "https://baeldung.com")
          .header("Access-Control-Request-Method", "GET")
          .header("Access-Control-Request-Headers", "X-Baeldung-Key"))
          .andExpect(status().isOk())
          .andExpect(header().string("Access-Control-Allow-Headers", "X-Baeldung-Key"));
    }
    
    @Test
    @SneakyThrows
    void whenPreflightRequestWithNotAllowedHeader_thenDoesNotAllowCors() {
        mockMvc.perform(options("/api/v1/joke")
          .header("Origin", "https://baeldung.com")
          .header("Access-Control-Request-Method", "GET")
          .header("Access-Control-Request-Headers", "X-Non-Baeldung-Key"))
          .andExpect(status().isForbidden());
    }
    
    @Test
    @SneakyThrows
    void whenRequestFromAllowedOrigin_thenExposesHeader() {
        mockMvc.perform(get("/api/v1/joke")
          .header("Origin", "https://baeldung.com"))
          .andExpect(status().isOk())
          .andExpect(header().string("Access-Control-Expose-Headers", "X-Rate-Limit-Remaining"))
          .andExpect(header().exists("X-Rate-Limit-Remaining"));
    }

    @Test
    @SneakyThrows
    void whenRequestFromNotAllowedOrigin_thenDoesNotExposeHeader() {
        mockMvc.perform(get("/api/v1/joke")
          .header("Origin", "https://non-baeldung.com"))
          .andExpect(status().isForbidden())
          .andExpect(header().doesNotExist("Access-Control-Expose-Headers"))
          .andExpect(header().doesNotExist("X-Rate-Limit-Remaining"));
    }
    
}
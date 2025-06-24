package com.baeldung.openapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenApiApplication.class)
@ComponentScan("com.baeldung.openapi")
@AutoConfigureMockMvc
public class OpenApiPetsIntegrationTest {

    private static final String PETS_PATH = "/pets";

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class NoSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
            return http.build();
        }
    }

    @Test
    public void whenReadAll_thenStatusIsNotImplemented() throws Exception {
        this.mockMvc.perform(get(PETS_PATH))
            .andExpect(status().isNotImplemented());
    }

    @Test
    public void whenReadOne_thenStatusIsNotImplemented() throws Exception {
        this.mockMvc.perform(get(PETS_PATH +  "/"+ 1))
            .andExpect(status().isNotImplemented());
    }
}
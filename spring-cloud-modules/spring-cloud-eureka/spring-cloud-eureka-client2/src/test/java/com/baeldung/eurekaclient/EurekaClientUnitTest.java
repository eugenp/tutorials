package com.baeldung.eurekaclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(Controller.class)
class EurekaClientUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.application.name}")
    private String appName;

    @Test
    void greeting_shouldReturnHelloMessageWithAppName() throws Exception {
        String expectedMessage = String.format("Hello from '%s'!", appName);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string(expectedMessage));
    }

}
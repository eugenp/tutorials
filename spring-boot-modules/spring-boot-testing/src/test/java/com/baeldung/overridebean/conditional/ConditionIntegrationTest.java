package com.baeldung.overridebean.conditional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.overridebean.Endpoint;
import com.baeldung.overridebean.boot.Application;

@SpringBootTest(classes = { Application.class, ConditionalConfig.class, Endpoint.class, ConditionalTestConfig.class }, properties = "service.stub=true")
@AutoConfigureMockMvc
class ConditionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenConditionalConfig_whenServiceStubIsTrue_thenStubOk() throws Exception {
        this.mockMvc.perform(get("/hello"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("hello conditional stub")));
    }
}

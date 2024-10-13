package com.baeldung.overridebean.mockbean;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.overridebean.Endpoint;
import com.baeldung.overridebean.Service;
import com.baeldung.overridebean.boot.Application;

@SpringBootTest(classes = { Application.class, Endpoint.class })
@AutoConfigureMockMvc
class MockBeanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service;

    @Test
    void givenServiceMockBean_whenGetHelloEndpoint_thenMockOk() throws Exception {
        when(service.helloWorld()).thenReturn("hello mock bean");
        this.mockMvc.perform(get("/hello"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("hello mock bean")));
    }
}

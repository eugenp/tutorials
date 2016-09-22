package com.baeldung.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GreetControllerTest {

    private MockMvc mockMvc;
    private static final String CONTENT_TYPE = "application/json";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GreetController()).build();
    }

    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(get("/homePage")).andExpect(view().name("index"));
    }

    @Test
    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(get("/greet")).andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.message").value("Hello World!!!"));
    }

    @Test
    public void givenGreetURIWithPathVariable_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(get("/greetWithPathVariable/John")).andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.message").value("Hello World John!!!"));
    }

    @Test
    public void givenGreetURIWithPathVariable_2_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(get("/greetWithPathVariable/{name}", "Doe")).andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.message").value("Hello World Doe!!!"));
    }

    @Test
    public void givenGreetURIWithQueryParameter_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(get("/greetWithQueryVariable").param("name", "John Doe")).andDo(print()).andExpect(status().isOk())
          .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.message").value("Hello World John Doe!!!"));
    }

    @Test
    public void givenGreetURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPost")).andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
          .andExpect(jsonPath("$.message").value("Hello World!!!"));
    }

    @Test
    public void givenGreetURIWithPostAndFormData_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPostAndFormData").param("id", "1").param("name", "John Doe")).andDo(print()).andExpect(status().isOk())
          .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.message").value("Hello World John Doe!!!")).andExpect(jsonPath("$.id").value(1));
    }
}

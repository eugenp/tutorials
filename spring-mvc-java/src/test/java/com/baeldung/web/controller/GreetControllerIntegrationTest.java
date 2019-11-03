package com.baeldung.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.spring.web.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebConfig.class, WebConfig.class })
public class GreetControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        final ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("greetController"));
    }

    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/homePage")).andDo(print()).andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/greet")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!")).andReturn();
        Assert.assertEquals(CONTENT_TYPE, mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenGreetURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithPathVariable/John")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John!!!"));
    }

    @Test
    public void givenGreetURIWithPathVariable_2_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithPathVariable/{name}", "Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World Doe!!!"));
    }

    @Test
    public void givenGreetURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithQueryVariable").param("name", "John Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!"));
    }

    @Test
    public void givenGreetURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPost")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!"));
    }

    @Test
    public void givenGreetURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPostAndFormData").param("id", "1").param("name", "John Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE)).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!")).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}
package com.baeldung.spring.controller;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.spring.ApplicationConfig;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationConfig.class })
public class GreetControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void verifyWac() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("greetController"));
    }

    @Test
    public void verifyIndexJspViewName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/homePage")).andDo(print()).andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void verifyGreet() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/greet")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!")).andReturn();
        Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    public void verifyGreetWithPathVariable() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithPathVariable/John")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John!!!"));
    }

    @Test
    public void verifyGreetWithPathVariable_2() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithPathVariable/{name}", "Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World Doe!!!"));
    }

    @Test
    public void verifyGreetWithQueryVariable() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/greetWithQueryVariable").param("name", "John Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!"));
    }

    @Test
    public void verifyGreetWithPost() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPost")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!"));
    }

    @Test
    public void verifyGreetWithPostAndFormData() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/greetWithPostAndFormData").param("id", "1").param("name", "John Doe")).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!")).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
}
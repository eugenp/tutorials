package com.baeldung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.baeldung.controller.controller.GreetingsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { GreetingsController.class }, loader = AnnotationConfigWebContextLoader.class)
public class GreetingsControllerUnitTest {
    
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
    public void givenReturnTypeIsString_whenJacksonOnClasspath_thenDefaultContentTypeIsJSON() throws Exception {
        
        // Given
        String expectedMimeType = "application/json";
        
        // Then
        String actualMimeType = this.mockMvc.perform(MockMvcRequestBuilders.get("/greetings-with-response-body", 1)).andReturn().getResponse().getContentType();

        Assert.assertEquals(expectedMimeType, actualMimeType);
        
    }
    
    @Test
    public void givenReturnTypeIsResponseEntity_thenDefaultContentTypeIsJSON() throws Exception {
        
        // Given
        String expectedMimeType = "application/json";
        
        // Then
        String actualMimeType = this.mockMvc.perform(MockMvcRequestBuilders.get("/greetings-with-response-entity", 1)).andReturn().getResponse().getContentType();

        Assert.assertEquals(expectedMimeType, actualMimeType);
    }
}
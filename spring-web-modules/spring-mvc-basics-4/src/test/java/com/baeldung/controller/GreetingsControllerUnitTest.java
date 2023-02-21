package com.baeldung.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.validation.listvalidation.SpringListValidationApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringListValidationApplication.class)
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
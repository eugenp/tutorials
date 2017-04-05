package com.baeldung.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.spring.web.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class RequestMapingShortcutsIntegrationTest {
	
	@Autowired
    private WebApplicationContext ctx;
	
    private MockMvc mockMvc;
    
    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.ctx);
        this.mockMvc = builder.build();
    }

	@Test 
	public void giventUrl_whenGetRequest_thenFindGetResponse() throws Exception {
	    
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/get");
		
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("GET Response");
		
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test 
	public void giventUrl_whenPostRequest_thenFindPostResponse() throws Exception {
	    
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/post");
		
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("POST Response");
		
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test 
	public void giventUrl_whenPutRequest_thenFindPutResponse() throws Exception {
	    
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/put");
		
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("PUT Response");
		
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test 
	public void giventUrl_whenDeleteRequest_thenFindDeleteResponse() throws Exception {
	    
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/delete");
		
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("DELETE Response");
		
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Test 
	public void giventUrl_whenPatchRequest_thenFindPatchResponse() throws Exception {
	    
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/patch");
		
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().string("PATCH Response");
		
		this.mockMvc.perform(builder).andExpect(contentMatcher).andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}

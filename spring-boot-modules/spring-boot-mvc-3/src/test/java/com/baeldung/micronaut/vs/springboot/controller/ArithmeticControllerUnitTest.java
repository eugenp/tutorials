package com.baeldung.micronaut.vs.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.micronaut.vs.springboot.CompareApplication;

@SpringBootTest(classes = CompareApplication.class)
@AutoConfigureMockMvc
public class ArithmeticControllerUnitTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void givenTwoNumbers_whenAdd_thenCorrectAnswerReturned() throws Exception {
		Float expected = Float.valueOf(10 + 20);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/math/sum/10/20")
				.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().string(expected.toString()));
	}
	
	@Test
	public void givenTwoNumbers_whenSubtract_thenCorrectAnswerReturned() throws Exception {
		Float expected = Float.valueOf(20 - 10);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/math/subtract/20/10")
				.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().string(expected.toString()));
	}

	@Test
	public void givenTwoNumbers_whenMultiply_thenCorrectAnswerReturned() throws Exception {
		Float expected = Float.valueOf(20 * 10);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/math/multiply/20/10")
				.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().string(expected.toString()));
	}
	
	@Test
	public void givenTwoNumbers_whenDivide_thenCorrectAnswerReturned() throws Exception {
		Float expected = Float.valueOf(20 / 10);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/math/divide/20/10")
				.accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().string(expected.toString()));
	}
	
	@Test
	public void whenMemory_thenMemoryStringReturned() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/math/memory")
		    .accept(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk())
		    .andExpect(content().string(containsString("Initial:")));
	}
}

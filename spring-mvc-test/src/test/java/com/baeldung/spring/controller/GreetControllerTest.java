package com.baeldung.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class GreetControllerTest {
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new GreetController()).build();
	}
	
	@Test
	public void verifyIndexJspViewName() throws Exception {
		this.mockMvc.perform(get("/homePage"))
				.andExpect(view().name("index"));
	}
	
	@Test
	public void verifyGreet() throws Exception {
		this.mockMvc.perform(get("/greet"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.message").value("Hello World!!!"));
	}
	
	@Test
	public void verifyGreetWithPathVariable() throws Exception {
		this.mockMvc.perform(get("/greetWithPathVariable/John"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.message").value("Hello World John!!!"));
	}
	
	@Test
	public void verifyGreetWithPathVariable_2() throws Exception {
		this.mockMvc.perform(get("/greetWithPathVariable/{name}","Doe"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.message").value("Hello World Doe!!!"));
	}
	
	@Test
	public void verifyGreetWithQueryVariable() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/greetWithQueryVariable").param("name", "John Doe"))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!"));
	}

	@Test
	public void verifyGreetWithPost() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/greetWithPost"))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!"));
	}

	@Test
	public void verifyGreetWithPostAndFormData() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/greetWithPostAndFormData").param("id", "1").param("name", "John Doe"))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World John Doe!!!")).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}
}

package com.baeldung.dddhexagonalspring.application.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnagramsControllerTest {

	private static final String URL_PREFIX = "/anagrams/";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenWordsAreAnagrams_thenIsOK() throws Exception {
		String url = URL_PREFIX + "/Hello/hello";
		this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(
				content().string(containsString("{\"areAnagrams\":true}")));
	}

	@Test
	public void whenWordsAreNotAnagrams_thenIsOK() throws Exception {
		String url = URL_PREFIX + "/HelloDAD/HelloMOM";
		this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andExpect(
				content().string(containsString("{\"areAnagrams\":false}")));
	}

	@Test
	public void whenHttpRequestMethodNotSupported_thenMethodNotAllowed() throws Exception {
		String url = URL_PREFIX + "/string/string";
		this.mockMvc.perform(post(url)).andDo(print()).andExpect(status().isMethodNotAllowed()).andExpect(
				content().string(containsString("method is not supported for this request. Supported methods are")));
	}

	@Test
	public void whenFirstPathVariableConstraintViolation_thenBadRequest() throws Exception {
		String url = URL_PREFIX + "/11/string";
		this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isBadRequest()).andExpect(
				content().string(containsString("string1")));
	}

	@Test
	public void whenSecondPathVariableConstraintViolation_thenBadRequest() throws Exception {
		String url = URL_PREFIX + "/string/11";
		this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isBadRequest()).andExpect(
				content().string(containsString("string2")));
	}
}

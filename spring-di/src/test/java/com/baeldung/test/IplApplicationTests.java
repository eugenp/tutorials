package com.baeldung.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.baeldung.IplApplication.class)
public class IplApplicationTests {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testHome() throws Exception {
		this.mvc.perform(get("/api")).andExpect(status().isOk()).andExpect(content().string(containsString("IPL API")));
	}

	@Test
	public void getVenues() throws Exception {
		this.mvc.perform(get("/api/venues")).andExpect(status().isOk())
				.andExpect(content().json("[\"Kolkata\", \"Mumbai\", \"Mohali\", \"Bangalore\"]"));

	}

	@Test
	public void getSeasonFormat() throws Exception {

		this.mvc.perform(get("/api/season-format")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Round Robin")));
	}

}

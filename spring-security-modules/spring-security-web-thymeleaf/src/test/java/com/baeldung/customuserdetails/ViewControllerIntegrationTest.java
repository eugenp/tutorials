package com.baeldung.customuserdetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.baeldung.customuserdetails.PasswordEncoderConfiguration;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(PasswordEncoderConfiguration.class)
public class ViewControllerIntegrationTest {

	@Autowired
	private WebApplicationContext context;
	MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void givenUser_whenPerformingGet_thenReturnsIndex() throws Exception {
		mvc.perform(get("/index").with(user("user").password("password"))).andExpect(status().isOk()).andExpect(view().name("userdetails"));
	}

}

package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JmeterIntegrationTest {

	MockMvc mvc;

	public JmeterIntegrationTest(WebApplicationContext wac) {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	void whenCallingTestController_thenWeShouldRecieveRandomizedResponse() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/api/test"))
		  .andDo(print())
		  .andExpect(status().isOk())
		  .andReturn()
		  .getResponse();

		assertThat(response.getContentAsString())
		  .contains("Test message...");
	}

}

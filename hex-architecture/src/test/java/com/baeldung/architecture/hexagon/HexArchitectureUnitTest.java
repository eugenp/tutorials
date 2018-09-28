package com.baeldung.architecture.hexagon;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.architecture.hexagon.configuration.AppConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfiguration.class)
public class HexArchitectureUnitTest {
	@Autowired
	private WebApplicationContext webAppContext;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}

	@Test
	public void whenBrakesAreAppliedToCar_thenRetrievedStatusOk() throws Exception {
		mockMvc.perform(get("/vehicles/{vehicle}/brake", "car"))
        .andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void whenBrakesAreAppliedToWagon_thenRetrievedStatusOk() throws Exception {
		mockMvc.perform(get("/vehicles/{vehicle}/brake", "wagon"))
        .andExpect(status().isOk()).andReturn();
	}

}
package org.baeldung.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InjectionApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getIndex() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void test_addUser_success() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/user")
				.content("{\n" +
						"\"name\": \"BaeldungSpring\",\n" +
						"\"details\": {\"details\":\"userDetails\"},\n" +
						"\"address\": {\"address\":\"userAddress\"}\n" +
						"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void test_addUser_xmlConfig_success() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/xml-config/user")
				.content("{\n" +
						"\"name\": \"BaeldungSpring\",\n" +
						"\"details\": {\"details\":\"userDetails\"},\n" +
						"\"address\": {\"address\":\"userAddress\"}\n" +
						"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
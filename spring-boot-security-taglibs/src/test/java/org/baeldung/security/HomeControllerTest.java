package org.baeldung.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {
	
    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void home() throws Exception {
		String body = this.restTemplate.withBasicAuth("ADMIN", SecurityConfig.DEFAULT_PASSWORD).getForEntity("/", String.class).getBody();
		System.out.println(body);
		assertTrue(body.contains("AUTHENTICATED"));
		assertTrue(body.contains("ADMIN ROLE"));
	}
}

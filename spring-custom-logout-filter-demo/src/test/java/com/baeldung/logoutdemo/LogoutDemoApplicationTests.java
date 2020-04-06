package com.baeldung.logoutdemo;

import com.baeldung.logoutdemo.services.UserCache;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LogoutDemoApplication.class, MvcConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
		@Sql(value = "classpath:before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(value = "classpath:after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class LogoutDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserCache userCache;

	@LocalServerPort
	private int port;

	@Test
	public void testUserCacheLoginLogout() throws Exception {
		String languageUrl = "http://localhost:" + port + "/user/language";
		String logoutUrl = "http://localhost:" + port + "/user/logout";

		// User cache should be empty on start
		assertThat(userCache.size()).isEqualTo(0);

		// Request using first login
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("user", "pass")
				.getForEntity(languageUrl, String.class);

		assertThat(response.getBody()).contains("english");

		// User cache must contain the user
		assertThat(userCache.size()).isEqualTo(1);

		// Getting the session cookie
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

		// Request with the session cookie
		response = restTemplate
				.exchange(languageUrl, HttpMethod.GET, new HttpEntity(requestHeaders), String.class);
		assertThat(response.getBody()).contains("english");

		// Logging out using the session cookies
		response = restTemplate.exchange(logoutUrl, HttpMethod.GET, new HttpEntity(requestHeaders), String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(200);

		// User cache must be empty now
		// this is the reaction on custom logout filter execution
		assertThat(userCache.size()).isEqualTo(0);

		// Assert unathorized request
		response = restTemplate
				.exchange(languageUrl, HttpMethod.GET, new HttpEntity(requestHeaders), String.class);
		assertThat(response.getStatusCode().value()).isEqualTo(401);
	}

}

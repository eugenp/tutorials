package com.baeldung.spring.data.snowflake;

import com.baeldung.spring.data.snowflake.entity.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = SpringDataSnowflakeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@TestPropertySource("classpath:application-test.properties")
public class SpringDataSnowflakeApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DatabaseSetup(value = "/data/user.xml")
    @ExpectedDatabase("/data/user.xml")
    public void shouldReturnUserSuccessfully() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        Map<String, Long> params = new HashMap<>();
        params.put("id", 1L);
        ResponseEntity<User> responseEntity = restTemplate.exchange("/user/{id}", HttpMethod.GET, entity, User.class, params);
        assertEquals("Ryan", responseEntity.getBody().getName());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


	@Test
	@DatabaseSetup(value = "/data/user.xml")
	@ExpectedDatabase("/data/user.xml")
	public void shouldReturnAllUsersSuccessfully() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<List> responseEntity = restTemplate.exchange("/user/all", HttpMethod.GET, entity, List.class);
		assertEquals(2, responseEntity.getBody().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}

package com.baeldung.springbootsecurityrest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.springbootsecurityrest.basicauth.SpringBootSecurityApplication;
import com.baeldung.springbootsecurityrest.vo.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringBootSecurityApplication.class)
public class BasicAuthConfigurationIntegrationTest {

    TestRestTemplate restTemplate;
    URL base;

    @LocalServerPort int port;

    @Before
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL("http://localhost:" + port);
    }

    @Test
    public void givenCorrectCredentials_whenLogin_ThenSuccess() throws IllegalStateException, IOException {
    	restTemplate = new TestRestTemplate();
    	User user = new User();
    	user.setUserName("user");
    	user.setPassword("password");
        ResponseEntity<String> response = restTemplate.postForEntity(base.toString()+"/login",user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
          .getBody()
          .contains("true"));
    }
    
    @Test
    public void givenWrongCredentials_whenLogin_ThenReturnFalse() throws IllegalStateException, IOException {
    	restTemplate = new TestRestTemplate();
    	User user = new User();
    	user.setUserName("user");
    	user.setPassword("wrongpassword");
        ResponseEntity<String> response = restTemplate.postForEntity(base.toString()+"/login",user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
          .getBody()
          .contains("false"));
    }
    
    @Test
    public void givenLoggedInUser_whenRequestsHomePage_ThenSuccess() throws IllegalStateException, IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString()+"/user", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response
          .getBody()
          .contains("user"));
    }

    @Test
    public void givenWrongCredentials_whenRequestsHomePage_ThenUnauthorized() throws IllegalStateException, IOException {
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> response = restTemplate.getForEntity(base.toString()+"/user", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}

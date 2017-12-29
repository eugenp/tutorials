package com.baeldung.springbootsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("form")
public class FormConfigurationIntegrationTest {

    @Autowired TestRestTemplate restTemplate;
    @LocalServerPort int port;

    @Test
    public void whenLoginPageIsRequested_ThenSuccess() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        ResponseEntity<String> responseEntity = restTemplate.exchange("/login", HttpMethod.GET, new HttpEntity<Void>(httpHeaders), String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity
          .getBody()
          .contains("_csrf"));
    }

    @Test
    public void whenTryingToLoginWithCorrectCredentials_ThenAuthenticateWithSuccess() {
        HttpHeaders httpHeaders = getHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = getFormSubmitCorrectCredentials();
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(form, httpHeaders), String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertTrue(responseEntity
          .getHeaders()
          .getLocation()
          .toString()
          .endsWith(this.port + "/"));
        assertNotNull(responseEntity
          .getHeaders()
          .get("Set-Cookie"));
    }

    @Test
    public void whenTryingToLoginWithInorrectCredentials_ThenAuthenticationFailed() {
        HttpHeaders httpHeaders = getHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> form = getFormSubmitIncorrectCredentials();
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/login", HttpMethod.POST, new HttpEntity<>(form, httpHeaders), String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertTrue(responseEntity
          .getHeaders()
          .getLocation()
          .toString()
          .endsWith(this.port + "/login?error"));
        assertNull(responseEntity
          .getHeaders()
          .get("Set-Cookie"));
    }

    private MultiValueMap<String, String> getFormSubmitCorrectCredentials() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("username", "user");
        form.set("password", "password");
        return form;
    }

    private MultiValueMap<String, String> getFormSubmitIncorrectCredentials() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("username", "user");
        form.set("password", "wrongpassword");
        return form;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> page = this.restTemplate.getForEntity("/login", String.class);
        assertEquals(page.getStatusCode(), HttpStatus.OK);
        String cookie = page
          .getHeaders()
          .getFirst("Set-Cookie");
        headers.set("Cookie", cookie);
        Pattern pattern = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*");
        Matcher matcher = pattern.matcher(page.getBody());
        assertTrue(matcher.matches());
        headers.set("X-CSRF-TOKEN", matcher.group(1));
        return headers;
    }

}

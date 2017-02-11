package com.baeldung.spring.cloud.bootstrap.gateway;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GatewayApplicationLiveTest {

    @Test
    public void testAccess() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String testUrl = "http://localhost:8080";

        ResponseEntity<String> response = testRestTemplate.getForEntity(testUrl + "/book-service/books", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        //try the protected resource and confirm the redirect to login
        response = testRestTemplate.getForEntity(testUrl + "/book-service/books/1", String.class);
        Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
        Assert.assertEquals("http://localhost:8080/login", response.getHeaders().get("Location").get(0));

        //login as user/password
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("username", "user");
        form.add("password", "password");
        response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        String sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        //request the protected resource
        response = testRestTemplate.exchange(testUrl + "/book-service/books/1", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        //request the admin protected resource to determine it is still protected
        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        //login as the admin
        form.clear();
        form.add("username", "admin");
        form.add("password", "admin");
        response = testRestTemplate.postForEntity(testUrl + "/login", form, String.class);

        //extract the session from the cookie and propagate it to the next request
        sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);
        httpEntity = new HttpEntity<>(headers);

        //request the protected resource
        response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());

        //request the discovery resources as the admin
        response = testRestTemplate.exchange(testUrl + "/discovery", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
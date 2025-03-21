package com.baeldung.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilterControllerUnitTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void givenRequestOnPublicService_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/securityNone/test", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("user1", "user1Pass")
                .getForEntity("/secured/test", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenRequestOnPrivateService_shouldFailWith401() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/secured/test", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void givenInvalidAuthRequestOnPrivateService_shouldSucceedWith401() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("spring", "wrong")
                .getForEntity("/secured/test", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
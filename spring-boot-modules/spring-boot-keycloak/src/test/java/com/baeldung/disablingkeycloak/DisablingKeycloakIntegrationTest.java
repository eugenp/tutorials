package com.baeldung.disablingkeycloak;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("disablingkeycloak")
public class DisablingKeycloakIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenUnauthenticated_whenGettingUser_shouldReturnUser() {
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/1", User.class);

        assertEquals(HttpStatus.SC_OK, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody()
            .getFirstname());
    }

}

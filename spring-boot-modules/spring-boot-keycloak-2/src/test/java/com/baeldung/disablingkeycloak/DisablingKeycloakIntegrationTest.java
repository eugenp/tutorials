package com.baeldung.disablingkeycloak;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("disablingkeycloak")
class DisablingKeycloakIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenUnauthenticated_whenGettingUser_shouldReturnUser() {
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/1", User.class);

        assertEquals(HttpStatus.SC_OK, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody()
            .getFirstname());
    }

}

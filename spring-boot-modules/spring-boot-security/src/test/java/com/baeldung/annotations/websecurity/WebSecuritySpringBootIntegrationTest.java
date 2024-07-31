package com.baeldung.annotations.websecurity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class WebSecuritySpringBootIntegrationTest {

    private static final String PUBLIC_RESOURCE = "/hello/baeldung.txt";
    private static final String HELLO_FROM_PUBLIC_RESOURCE = "Hello From Baeldung";

    @Autowired
    private ConfigSecuredController api;

    @Autowired
    private TestRestTemplate template;

    @Test
    void whenCallPublicDirectly_thenOk() {
        assertThat(api.publicHello()).isEqualTo("Hello Public");
    }

    @Test
    void whenCallProtectedDirectly_thenNoSecurity() {
        assertThat(api.protectedHello()).isEqualTo("Hello from protected");
    }

    @Test
    void whenGetProtectedViaWeb_thenForbidden() {
        ResponseEntity<String> result = template.getForEntity("/protected", String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void whenGetAdminViaWeb_thenForbidden() {
        ResponseEntity<String> result = template.getForEntity("/admin", String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void whenGetPublicViaWeb_thenSuccess() {
        ResponseEntity<String> result = template.getForEntity("/public", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Disabled("Fix this")
    @Test
    void givenPublicResource_whenGetViaWeb_thenOk() {
        ResponseEntity<String> result = template.getForEntity(PUBLIC_RESOURCE, String.class);
        assertEquals(HELLO_FROM_PUBLIC_RESOURCE, result.getBody());
    }

}

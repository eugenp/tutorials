package com.baeldung.annotations.websecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebSecuritySpringBootIntegrationTest {
    private static final String PUBLIC_RESOURCE = "/hello/baeldung.txt";
    private static final String HELLO_FROM_PUBLIC_RESOURCE = "Hello From Baeldung";

    @Autowired
    private ConfigSecuredController api;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void whenCallPublicDirectly_thenOk() {
        assertThat(api.publicHello()).isEqualTo("Hello Public");
    }

    @Test
    public void whenCallProtectedDirectly_thenNoSecurity() {
        assertThat(api.protectedHello()).isEqualTo("Hello from protected");
    }

    @Test
    public void whenGetProtectedViaWeb_thenForbidden() {
        ResponseEntity<String> result = template.getForEntity("/protected", String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void whenGetAdminViaWeb_thenForbidden() {
        ResponseEntity<String> result = template.getForEntity("/admin", String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void whenGetPublicViaWeb_thenSuccess() {
        ResponseEntity<String> result = template.getForEntity("/public", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenPublicResource_whenGetViaWeb_thenOk() {
        ResponseEntity<String> result = template.getForEntity(PUBLIC_RESOURCE, String.class);
        assertEquals(HELLO_FROM_PUBLIC_RESOURCE, result.getBody());
    }

}

package com.baeldung.springsecuritytaglibs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootSecurityTagLibsApplication.class)
class HomeControllerUnitTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenUserIsAuthenticatedThenAuthenticatedSectionsShowOnSite() {
        String body = this.restTemplate.withBasicAuth("testUser", "password")
            .getForEntity("/", String.class)
            .getBody();

        // test <sec:authorize access="!isAuthenticated()">
        assertFalse(body.contains("Login"));

        // test <sec:authorize access="isAuthenticated()">
        assertTrue(body.contains("Logout"));

        // test <sec:authorize access="hasRole('ADMIN')">
        assertTrue(body.contains("Manage Users"));

        // test <sec:authentication property="principal.username" />
        assertTrue(body.contains("testUser"));

        // test <sec:authorize url="/adminOnlyURL">
        assertTrue(body.contains("<a href=\"/userManagement\">"));

        // test <sec:csrfInput />
        assertTrue(body.contains("<input type=\"hidden\" name=\"_csrf\" value=\""));

        // test <sec:csrfMetaTags />
        assertTrue(body.contains("<meta name=\"_csrf_parameter\" content=\"_csrf\" />"));
    }

    @Test
    void whenUserIsNotAuthenticatedThenOnlyAnonymousSectionsShowOnSite() throws Exception {
        String body = this.restTemplate.getForEntity("/", String.class)
            .getBody();

        // test <sec:authorize access="!isAuthenticated()">
        assertTrue(body.contains("Login"));

        // test <sec:authorize access="isAuthenticated()">
        assertFalse(body.contains("Logout"));
    }
}

package org.baeldung.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void home() throws Exception {
        String body = this.restTemplate.withBasicAuth("ADMIN", SecurityConfig.DEFAULT_PASSWORD)
            .getForEntity("/", String.class)
            .getBody();
        System.out.println(body);

        // test <sec:authorize access="isAuthenticated()">
        assertTrue(body.contains("AUTHENTICATED"));

        // test <sec:authorize access="hasRole('ADMIN')">
        assertTrue(body.contains("ADMIN ROLE"));

        // test <sec:authentication property="principal.username" />
        assertTrue(body.contains("principal.username: ADMIN"));

        // test <sec:csrfInput />
        assertTrue(body.contains("<input type=\"hidden\" name=\"_csrf\" value=\""));

        // test <sec:csrfMetaTags />
        assertTrue(body.contains("<meta name=\"_csrf_parameter\" content=\"_csrf\" />"));
    }
}

package com.baeldung;

import com.baeldung.web.PathPatternController;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class)
public class PathPatternWithPathVariableUsingHandlerMethodTest {

    private static WebTestClient client;

    @BeforeClass
    public static void setUp() {
        client = WebTestClient.bindToController(new PathPatternController()).build();
    }

    @Test
    public void givenHandlerMethod_whenURLWithPathVariable_then404() {
        //This should call the "/test/{*id}" 
        //But it does not work and logs the same error as mentioned in https://jira.spring.io/browse/SPR-15558
        client
                .get()
                .uri("/test/ab/cd")
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}

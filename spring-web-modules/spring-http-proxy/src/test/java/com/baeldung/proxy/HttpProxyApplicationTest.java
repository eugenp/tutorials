package com.baeldung.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HttpProxyApplicationTest {

    @Autowired
    private MyHttpClient webClient;

    @Autowired
    private MyHttpClient restTemplateClient;

    @Test
    public void testGreet() {
        String response = webClient.greet();
        Assertions.assertEquals("Hello from the server!", response);
    }

    @Test
    public void testGreetWithUsername() {
        String response = webClient.greet("John");
        Assertions.assertEquals("Hello, John!", response);
    }

    @Test
    public void testGreetWithRestTemplate() {
        String response = restTemplateClient.greet();
        Assertions.assertEquals("Hello from the server!", response);
    }

    @Test
    public void testGreetWithUsernameRestTemplate() {
        String response = restTemplateClient.greet("Jane");
        Assertions.assertEquals("Hello, Jane!", response);
    }
}
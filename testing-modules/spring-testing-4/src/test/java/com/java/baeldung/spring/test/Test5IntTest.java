package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test5IntTest extends AbstractWebIntTest {

    // will inject actual dynamic port
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldInjectLocalServerPort() {
        assertTrue(port > 0, "port is not initialized");
    }

    @Test
    public void testGetArticleReturns200() {
        var entity = testRestTemplate.getForEntity("/articles/1", String.class);
        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Content 1", entity.getBody());
    }
}

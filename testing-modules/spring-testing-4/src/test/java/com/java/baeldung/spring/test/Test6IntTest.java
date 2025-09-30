package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test6IntTest extends AbstractWebIntTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetArticlesReturns404() {
        var entity = testRestTemplate.getForEntity("/articles", String.class);
        assertEquals(404, entity.getStatusCode().value());
    }
}

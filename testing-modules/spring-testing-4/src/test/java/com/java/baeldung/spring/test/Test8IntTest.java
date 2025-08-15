package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test8IntTest extends AbstractWebIntTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testPostArticlesReturns405() {
        var entity = testRestTemplate.postForEntity("/articles/1", new byte[0], String.class);
        assertEquals(405, entity.getStatusCode().value());
    }
}

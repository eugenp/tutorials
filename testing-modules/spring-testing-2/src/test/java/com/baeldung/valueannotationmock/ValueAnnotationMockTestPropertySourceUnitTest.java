package com.baeldung.valueannotationmock;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ValueAnnotationMock.class)
@SpringBootTest
@TestPropertySource(properties = {
    "external.api.url=http://mocked-url.com"
})
public class ValueAnnotationMockTestPropertySourceUnitTest {

    @Autowired
    private ValueAnnotationMock valueAnnotationMock;

    @Test
    public void givenValue_whenUsingTestPropertySource_thenMockValueAnnotation() {
        String apiUrl = valueAnnotationMock.getApiUrl();
        assertEquals("http://mocked-url.com", apiUrl);
    }
}


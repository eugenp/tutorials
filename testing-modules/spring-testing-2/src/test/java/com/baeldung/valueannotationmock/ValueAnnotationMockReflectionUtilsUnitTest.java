package com.baeldung.valueannotationmock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class ValueAnnotationMockReflectionUtilsUnitTest {

    @Autowired
    private ValueAnnotationMock valueAnnotationMock;

    @Test
    public void givenValue_whenUsingReflectionUtils_thenMockValueAnnotation() {
        valueAnnotationMock = new ValueAnnotationMock();
        ReflectionTestUtils.setField(valueAnnotationMock, "apiUrl", "http://mocked-url.com");
        String apiUrl = valueAnnotationMock.getApiUrl();
        assertEquals("http://mocked-url.com", apiUrl);
    }
}

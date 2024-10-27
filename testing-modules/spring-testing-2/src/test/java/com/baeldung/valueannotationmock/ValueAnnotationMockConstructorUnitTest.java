package com.baeldung.valueannotationmock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValueAnnotationMockConstructorUnitTest {

    private ValueAnnotationConstructorMock valueAnnotationConstructorMock;

    @BeforeEach
    public void setUp() {
        valueAnnotationConstructorMock = new ValueAnnotationConstructorMock("testUrl", "testPassword");
    }

    @Test
    public void testDefaultUrl() {
        assertEquals("testUrl", valueAnnotationConstructorMock.getApiUrl());
    }

    @Test
    public void testDefaultPassword() {
        assertEquals("testPassword", valueAnnotationConstructorMock.getApiPassword());
    }
}

package com.java.baeldung.spring.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class Test3IntTest extends AbstractIntegrationTest {

    @Autowired
    private SampleService sampleService;

    // declaring MockBean leads to a separate spring context for this test class
    @MockBean
    private SampleBean sampleBean;

    @BeforeEach
    public void setUp() {
        when(sampleBean.getValue()).thenReturn("mock");
    }

    @Test
    public void test() {
        assertEquals("mock", sampleBean.getValue());
    }
}

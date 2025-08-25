package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test2IntTest extends AbstractIntegrationTest {

    @Autowired
    private SampleBean rootBean;

    @Test
    public void test() {
        System.out.println("Test2IT.test " + rootBean);
        assertEquals("default", rootBean.getValue());
    }
}

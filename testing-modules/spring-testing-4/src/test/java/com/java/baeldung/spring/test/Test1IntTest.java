package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test1IntTest extends AbstractIntegrationTest {

    @Autowired
    private SampleBean rootBean;

    @Test
    public void test() {
        System.out.println("Test1IT.test " + rootBean);
        assertEquals("default", rootBean.getValue());
    }

    @Nested
    public class NestedIT {

        @Autowired
        private SampleBean nestedBean;

        @Test
        public void nested() {
            System.out.println("Test1IT.NestedTest.test " + nestedBean);
        }

        @Nested
        public class DeeplyNestedIT {

            @Autowired
            private SampleService sampleService;

            @Test
            public void deeplyNested() {
                assertEquals("default", sampleService.getValue());
                System.out.println("Test1IT.NestedTest.DeeplyNestedTest.deeplyNested " + sampleService);
            }
        }
    }

}

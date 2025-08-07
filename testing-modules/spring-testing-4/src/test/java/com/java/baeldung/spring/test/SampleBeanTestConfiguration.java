package com.java.baeldung.spring.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import({
        SampleBean.class,
        SampleService.class
})
@TestConfiguration
public class SampleBeanTestConfiguration {

}

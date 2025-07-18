package com.baeldung.samples;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "application.registration-v7=false")
class BeanRegistrationsSpring6Test {

    @Autowired
    MyService myService;

    @Test
    void shouldCreateBean() {
        assertThat(myService).isNotNull();
    }

}

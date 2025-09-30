package com.baeldung.spring.beanregistrar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeanRegistrarIntegrationTest {

    @Autowired
    MyService myService;

    @Test
    void whenRunningPlatform_thenRegisterBean() {
        assertThat(myService).isNotNull();
    }

}

package com.baeldung.dependsonconfig;

import  com.baeldung.application.dependsonconfig.DependsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DependsConfig.class)
class DependsConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenDependsOnBeans_whenLoaded_thenOrderIsMaintained() {
        String first = context.getBean("firstBean", String.class);
        String second = context.getBean("secondBean", String.class);

        assertThat(first).isEqualTo("FirstBean");
        assertThat(second).isEqualTo("SecondBeanAfterFirst");
    }
}


package com.baeldung.defaultconfig;

import com.baeldung.application.defaultconfig.ConfigA;
import com.baeldung.application.defaultconfig.ConfigB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ConfigA.class, ConfigB.class})
class DefaultConfigOrderUnitTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenConfigsWithoutOrder_whenLoaded_thenBeansExistRegardlessOfOrder() {
        assertThat(context.getBean("beanA")).isEqualTo("Bean A");
        assertThat(context.getBean("beanB")).isEqualTo("Bean B");
    }
}

package com.baeldung.autoconfig;

import com.baeldung.application.autoconfig.FirstAutoConfig;
import com.baeldung.application.autoconfig.SecondAutoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {SecondAutoConfig.class, FirstAutoConfig.class})
class AutoConfigOrderUnitTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenAutoConfigs_whenLoaded_thenOrderFollowsAnnotations() {
        String beanOne = context.getBean("autoBeanOne", String.class);
        String beanTwo = context.getBean("autoBeanTwo", String.class);

        assertThat(beanOne).isEqualTo("AutoBeanOne");
        assertThat(beanTwo).isEqualTo("AutoBeanTwoAfterOne");
    }
}

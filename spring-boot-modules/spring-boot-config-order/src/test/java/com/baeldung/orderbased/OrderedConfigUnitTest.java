package com.baeldung.orderbased;

import com.baeldung.application.orderbased.ConfigOne;
import com.baeldung.application.orderbased.ConfigTwo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ConfigTwo.class, ConfigOne.class})
class OrderedConfigUnitTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void givenOrderedConfigs_whenLoaded_thenOrderIsRespected() {
        String beanOne = context.getBean("configOneBean", String.class);
        String beanTwo = context.getBean("configTwoBean", String.class);

        assertThat(beanOne).isEqualTo("ConfigOneBean");
        assertThat(beanTwo).isEqualTo("ConfigTwoBean");
    }
}


package com.baeldung.beandefinitionoverrideexception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration1.class, TestConfiguration2.class}, properties = {"spring.main.allow-bean-definition-overriding=true"})
public class SpringBootBeanDefinitionOverrideExceptionIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void whenBeanOverridingAllowed_thenTestBean2OverridesTestBean1() {
        Object testBean = applicationContext.getBean("testBean");

        assertThat(testBean.getClass()).isEqualTo(TestConfiguration2.TestBean2.class);
    }
}

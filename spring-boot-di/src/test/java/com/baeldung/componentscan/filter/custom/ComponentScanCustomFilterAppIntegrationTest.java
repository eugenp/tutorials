package com.baeldung.componentscan.filter.custom;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComponentScanCustomFilterApp.class)
public class ComponentScanCustomFilterAppIntegrationTest {

    @Test
    public void whenCustomFilterIsUsed_thenComponentScanShouldRegisterBeanMatchingCustomFilter() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanCustomFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
            .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanCustomFilterApp"))
            .collect(Collectors.toList());
        assertThat(beans.size(), equalTo(1));
        assertThat(beans.get(0), equalTo("cat"));
    }
}

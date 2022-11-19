package com.baeldung.componentscan.filter.assignable;


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
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComponentScanAssignableTypeFilterApp.class)
public class ComponentScanAssignableTypeFilterAppIntegrationTest {

    @Test
    public void whenAssignableTypeFilterIsUsed_thenComponentScanShouldRegisterBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanAssignableTypeFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
            .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanAssignableTypeFilterApp"))
            .collect(Collectors.toList());
        assertThat(beans.size(), equalTo(2));
        assertThat(beans.contains("cat"), equalTo(true));
        assertThat(beans.contains("elephant"), equalTo(true));
    }
}

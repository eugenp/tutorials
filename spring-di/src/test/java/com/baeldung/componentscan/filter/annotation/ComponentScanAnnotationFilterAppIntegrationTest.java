package com.baeldung.componentscan.filter.annotation;

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
@SpringBootTest(classes = ComponentScanAnnotationFilterApp.class)
public class ComponentScanAnnotationFilterAppIntegrationTest {

    @Test
    public void whenAnnotationFilterIsUsed_thenComponentScanShouldRegisterBeanAnnotatedWithAnimalAnootation() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanAnnotationFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
            .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanAnnotationFilterApp"))
            .collect(Collectors.toList());
        assertThat(beans.size(), equalTo(1));
        assertThat(beans.get(0), equalTo("elephant"));
    }
}

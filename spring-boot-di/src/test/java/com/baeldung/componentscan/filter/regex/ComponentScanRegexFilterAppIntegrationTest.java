package com.baeldung.componentscan.filter.regex;

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
@SpringBootTest(classes = ComponentScanRegexFilterApp.class)
public class ComponentScanRegexFilterAppIntegrationTest {

    @Test
    public void testBean() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanRegexFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
            .filter(bean -> !bean.contains("org.springframework") && !bean.contains("componentScanRegexFilterApp"))
            .collect(Collectors.toList());
        assertThat(beans.size(), equalTo(2));
        assertThat(beans.contains("cat"), equalTo(true));
        assertThat(beans.contains("elephant"), equalTo(true));
    }
}

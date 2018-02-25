package com.baeldung.bean;

import com.baeldung.injection.SpringSetterBeanInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSetterBeanInjectionTest {

    @Autowired
    private SpringSetterBeanInjection springSetterBeanInjection;

    @Test
    public void setterBeanInjectionTest() {
        assertNotNull(springSetterBeanInjection);
        assertNotNull(springSetterBeanInjection.getSpringAutowiredBean());
        assertNotNull(springSetterBeanInjection.getSpringInjectBean());
        assertNotNull(springSetterBeanInjection.getSpringResourceBean());
    }

}

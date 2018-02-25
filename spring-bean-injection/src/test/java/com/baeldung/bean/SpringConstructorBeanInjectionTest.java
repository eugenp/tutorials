package com.baeldung.bean;

import com.baeldung.injection.SpringConstructorBeanInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringConstructorBeanInjectionTest {

    @Autowired
    private SpringConstructorBeanInjection springConstructorAutowiredBeanInjection;

    @Test
    public void constructorBeanInjectionTest() {
        assertNotNull(springConstructorAutowiredBeanInjection);
        assertNotNull(springConstructorAutowiredBeanInjection.getSpringAnnotatedBean());
    }

}

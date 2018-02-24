package com.baeldung.bean;

import com.baeldung.injection.SpringPropertyBeanInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringPropertyBeanInjectionTest {

    @Autowired
    private SpringPropertyBeanInjection springPropertyBeanInjection;

    @Test
    public void propertyBeanInjectionTest() {
        assertNotNull(springPropertyBeanInjection);
        assertNotNull(springPropertyBeanInjection.getAutowiredInjection());
        assertNotNull(springPropertyBeanInjection.getInjectInjection());
        assertNotNull(springPropertyBeanInjection.getResourceInjection());
    }

}

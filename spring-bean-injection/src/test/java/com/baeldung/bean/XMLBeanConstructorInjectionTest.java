package com.baeldung.bean;

import com.baeldung.injection.XMLBeanConstructorInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XMLBeanConstructorInjectionTest {

    @Autowired
    private XMLBeanConstructorInjection xmlBeanConstructorInjection;

    @Test
    public void xmlConstructorBeanInjectionTest() {
        assertNotNull(xmlBeanConstructorInjection);
        assertNotNull(xmlBeanConstructorInjection.getSpringAnnotatedBean());
    }

}

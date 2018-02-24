package com.baeldung.bean;

import com.baeldung.injection.XMLBeanSetterInjection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XMLBeanSetterInjectionTest {

    @Autowired
    private XMLBeanSetterInjection xmlBeanSetterInjection;

    @Test
    public void xmlConstructorBeanInjectionTest() {
        assertNotNull(xmlBeanSetterInjection);
        assertNotNull(xmlBeanSetterInjection.getSpringAnnotatedBean());
    }

}

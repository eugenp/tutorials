package com.baeldung.inject;

import com.baeldung.inject.xml.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Oleg Cherednik
 * @since 27.01.2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/service-constructor-single.xml")
public class XmlConstructorInjectionSingleTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void someServiceShouldInjectAnotherService() {
        SomeService someService = context.getBean(SomeService.class);
        assertNotNull(someService.getA());
        assertNull(someService.getB());
    }

}

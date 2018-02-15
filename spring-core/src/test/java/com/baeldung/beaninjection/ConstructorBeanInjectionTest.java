package com.baeldung.beaninjection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class ConstructorBeanInjectionTest {

    @Autowired
    private InternalServiceOne serviceOne;

    @Test
    public void whenInjectServiceOne_TheSuccess() {
        assertNotNull(serviceOne);
        assertEquals("componentOne", serviceOne.callComponentOne());
        assertEquals("componentTwo", serviceOne.callComponentTwo());
    }

}

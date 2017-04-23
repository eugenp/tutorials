package com.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class BeanInjectionWithJavaConfigTest {

    @Autowired
    private TaskLogger taskLogger;

    @Autowired
    private DutyLogger dutyLogger;

    @Test
    public void testConstructorBeanInjectionByJavaConfig() {

        assertThat(taskLogger.log(), equalTo("I am a task logger"));
    }

    @Test
    public void testSetterBeanInjectionByJavaConfig() {

        assertThat(dutyLogger.log(), equalTo("I am a duty logger"));
    }
}
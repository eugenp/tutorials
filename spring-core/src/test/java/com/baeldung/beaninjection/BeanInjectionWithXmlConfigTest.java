package com.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:constructor.xml", "classpath:setter.xml"})
public class BeanInjectionWithXmlConfigTest {

    @Autowired
    private TaskLogger taskLogger;

    @Autowired
    private DutyLogger dutyLogger;

    @Test
    public void testConstructorBeanInjectionByXmlConfig() {
        assertThat(taskLogger.log(), equalTo("I am a task logger"));
    }

    @Test
    public void testSetterBeanInjectionByXmlConfig() {
        assertThat(dutyLogger.log(), equalTo("I am a duty logger"));
    }
}
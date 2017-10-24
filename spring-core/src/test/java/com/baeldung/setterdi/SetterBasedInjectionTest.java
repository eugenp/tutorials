package com.baeldung.setterdi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.setterdi.domain.Room;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnotherConfig.class)
public class SetterBasedInjectionTest {

    @Autowired
    private Room room;

    @Test
    public void givenBeanDefinedInConfigClass_WhenDependencyInSetter_ThenDependencyInjected() {
        assertNotNull(room);
        assertNotNull(room.getBed());
    }

    @Test
    public void givenBeanDefinedInXml_WhenDependencyInSetter_ThenDependencyInjected() {
        ApplicationContext context = new ClassPathXmlApplicationContext("setterdi.xml");
        Room room = (Room) context.getBean("room");

        assertNotNull(room);
        assertNotNull(room.getBed());
    }

    @Test
    public void givenBeanScanned_WhenDependencyInSetter_ThenDependencyInjected() {
        ApplicationContext context = new ClassPathXmlApplicationContext("component-scan-setterdi.xml");
        Room room = (Room) context.getBean("room");

        assertNotNull(room);
        assertNotNull(room.getBed());
    }

}

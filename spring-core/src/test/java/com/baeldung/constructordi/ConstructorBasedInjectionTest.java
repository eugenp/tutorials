package com.baeldung.constructordi;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.constructordi.domain.Room;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnotherConfig.class)
public class ConstructorBasedInjectionTest {

    @Autowired
    private Room room;

    @Test
    public void givenBeanDefinedInConfigClass_WhenDependencyInConstructor_ThenDependencyInjected() {
        assertNotNull(room);
        assertNotNull(room.getBed());
    }

    @Test
    public void givenBeanDefinedInXml_WhenDependencyInConstructor_ThenDependencyInjected() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");
        Room room = (Room) context.getBean("room");

        assertNotNull(room);
        assertNotNull(room.getBed());
    }

    @Test
    public void givenBeanScanned_WhenDependencyInConstructor_ThenDependencyInjected() {
        ApplicationContext context = new ClassPathXmlApplicationContext("component-scan-constructordi.xml");
        Room room = (Room) context.getBean("room");

        assertNotNull(room);
        assertNotNull(room.getBed());
    }

}

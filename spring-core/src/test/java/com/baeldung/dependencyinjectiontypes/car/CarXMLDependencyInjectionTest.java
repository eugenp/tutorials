package com.baeldung.dependencyinjectiontypes.car;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CarXMLDependencyInjectionTest {

    @Test
    public void givenXMLBean_WhenSetOnConstructor_ThenDependencyValid () {

        ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
        CarWithConstructorInjection carWithConstructor = (CarWithConstructorInjection) context.getBean("carWithConstructorInjectionBean");


        assertNotNull(carWithConstructor);

        Engine engine = new Engine("V8", 335);
        assertEquals(carWithConstructor.race(), engine.turnOn());
    }

    @Test
    public void givenXMLBean_WhenSetOnSetter_ThenDependencyValid() {
        ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
        CarWithSetterInjection carWithSetter = (CarWithSetterInjection) context.getBean("carWithSetterInjectionBean");

        assertNotNull(carWithSetter);

        Engine engine = new Engine("V12", 645);
        assertEquals(carWithSetter.race(), engine.turnOn());
    }
}

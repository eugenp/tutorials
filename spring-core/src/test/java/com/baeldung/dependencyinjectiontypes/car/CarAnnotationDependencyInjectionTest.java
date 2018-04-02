package com.baeldung.dependencyinjectiontypes.car;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CarConfig.class })
public class CarAnnotationDependencyInjectionTest {

    @Autowired
    private CarWithConstructorInjection carWithConstructor;

    @Autowired
    private CarWithSetterInjection carWithSetter;

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid () {
        assertNotNull(carWithConstructor);

        Engine engine = new Engine("V12", 355);
        assertEquals(carWithConstructor.race(), engine.turnOn());
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        assertNotNull(carWithSetter);

        Engine engine = new Engine("V12", 355);
        assertEquals(carWithSetter.race(), engine.turnOn());
    }
}

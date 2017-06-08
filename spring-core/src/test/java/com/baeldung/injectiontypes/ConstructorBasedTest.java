package com.baeldung.injectiontypes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.injectiontypes.constructorbased.config.ConstructorBasedSpringCarsConfig;
import com.baeldung.injectiontypes.constructorbased.model.Car;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConstructorBasedSpringCarsConfig.class, loader = AnnotationConfigContextLoader.class)
public class ConstructorBasedTest {

    @Test
    public void whenCreatingACivic_thenPropertiesSet() {
        Car civic = this.getCarFromSpring();
    
        System.out.println(civic);

        assertEquals(civic.getBrand(), "Honda");
        assertEquals(civic.getModel(), "Civic");
        assertNotNull(civic.getEngine());
        assertEquals(civic.getEngine().getType(), "v4");
    }

    @SuppressWarnings("resource")
    private Car getCarFromSpring() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorBasedSpringCarsConfig.class);

        Car result = context.getBean(Car.class);

        return result;
    }
}

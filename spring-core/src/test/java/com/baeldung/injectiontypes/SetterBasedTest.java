package com.baeldung.injectiontypes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.injectiontypes.setterbased.config.SetterBasedSpringCarsConfig;
import com.baeldung.injectiontypes.setterbased.model.Car;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SetterBasedSpringCarsConfig.class, loader = AnnotationConfigContextLoader.class)
public class SetterBasedTest {

    @Test
    public void whenCreatingAMustang_thenPropertiesSet() {
        Car mustang = getCarFromSpring();

        System.out.println(mustang);

        assertEquals(mustang.getBrand(), "Ford");
        assertEquals(mustang.getModel(), "Mustang");
        assertNotNull(mustang.getEngine());
        assertEquals(mustang.getEngine().getType(), "v8");
    }

    @SuppressWarnings("resource")
    private static Car getCarFromSpring() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SetterBasedSpringCarsConfig.class);

        Car result = context.getBean(Car.class);

        return result;
    }
}

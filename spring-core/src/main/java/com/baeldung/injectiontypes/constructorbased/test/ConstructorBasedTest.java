package com.baeldung.injectiontypes.constructorbased.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injectiontypes.constructorbased.config.ConstructorBasedSpringCarsConfig;
import com.baeldung.injectiontypes.constructorbased.model.Car;

public class ConstructorBasedTest {

    public static void main(String[] args) {
        ConstructorBasedTest tests = new ConstructorBasedTest();

        tests.whenCreatingACivic_thenPropertiesSet();
    }

    @Test
    public void whenCreatingACivic_thenPropertiesSet() {
        Car civic = getCarFromSpring();

        System.out.println(civic);

        assert (civic.getBrand().equals("Honda"));
        assert (civic.getModel().equals("Civic"));
        assert (civic.getEngine() != null);
        assert (civic.getEngine().getType().equals("v4"));
    }

    @SuppressWarnings("resource")
    private static Car getCarFromSpring() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorBasedSpringCarsConfig.class);

        Car result = context.getBean(Car.class);

        return result;
    }
}

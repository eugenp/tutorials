package com.baeldung.constructordi;

import static org.junit.Assert.assertNotNull;

import org.springframework.context.ApplicationContext;

import com.baeldung.constructordi.domain.Car;

public class DependencyInjectionTestHelper {

    public static final void checkCarInitializedWithDependencies(ApplicationContext context) {
        // Get car from application context
        Car car = context.getBean(Car.class);

        assertNotNull("Injected car is null", car);
        assertNotNull("Car engine is null", car.getEngine());
        assertNotNull("Car transmission is null", car.getTransmission());
    }
}
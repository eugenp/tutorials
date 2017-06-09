package org.sgitario.spring.di;

import org.junit.Test;
import org.sgitario.spring.di.bean.CarWithConstructor;
import org.sgitario.spring.di.bean.DieselEngine;

public class WithoutDependencyInjectionTest {

    @Test
    public void whenCreateCarWithDieselEngine_thenEngineSaysDiesel() {
        CarWithConstructor dieselCar = new CarWithConstructor(new DieselEngine());
        dieselCar.run();
    }

}

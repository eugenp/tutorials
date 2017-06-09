package org.sgitario.spring.di;

import org.junit.Before;
import org.junit.Test;
import org.sgitario.spring.di.bean.AppAnnotationBasedConfig;
import org.sgitario.spring.di.bean.CarWithConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConstructorBasedInjectionTest {

    private ApplicationContext ctx;

    @Before
    public void setup() {
        ctx = new AnnotationConfigApplicationContext(AppAnnotationBasedConfig.class);
    }

    @Test
    public void whenGettingCar_AndOnlyDieselEngineBean_thenEngineShouldBeDiesel() {
        CarWithConstructor dieselCar = ctx.getBean(CarWithConstructor.class);
        dieselCar.run();
    }
}

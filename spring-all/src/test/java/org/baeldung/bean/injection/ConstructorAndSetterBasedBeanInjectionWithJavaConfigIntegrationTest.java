package org.baeldung.bean.injection;

import org.baeldung.bean.config.ConstructorAndSetterBasedCarAndMotorcycleConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConstructorAndSetterBasedBeanInjectionWithJavaConfigIntegrationTest {

    private static final int version = 1;

    @Test
    public void givenJavaConfigFile_whenUsingConstructorAndSetterBasedBeanInjection_thenCorrectBrake() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ConstructorAndSetterBasedCarAndMotorcycleConfig.class);
        ctx.refresh();

        Car car = ctx.getBean(Car.class);
        MotorCycle motorCycle = ctx.getBean(MotorCycle.class);

        Assert.assertEquals(version, car.getBrake().getVersion());
        Assert.assertEquals(version, motorCycle.getBrake().getVersion());
    }
}

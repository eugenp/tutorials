package com.baeldung.injectiontypes.setterbased.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injectiontypes.setterbased.config.SetterBasedSpringCarsConfig;
import com.baeldung.injectiontypes.setterbased.model.Car;

public class SetterBasedTest {

    public static void main(String[] args) {
        SetterBasedTest tests = new SetterBasedTest();

        tests.whenCreatingAMustang_thenPropertiesSet();
    }

    @Test
    public void whenCreatingAMustang_thenPropertiesSet() {
        Car mustang = getCarFromSpring();

        System.out.println(mustang);

        assert (mustang.getBrand().equals("Ford"));
        assert (mustang.getModel().equals("Mustang"));
        assert (mustang.getEngine().getType().equals("v8"));
    }

    @SuppressWarnings("resource")
    private static Car getCarFromSpring() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SetterBasedSpringCarsConfig.class);

        Car result = context.getBean(Car.class);

        return result;
    }
}

package com.baeldung.differentdi;

import com.baeldung.differentdi.model.Engine;
import com.baeldung.differentdi.model.constructordi.ConstructorBasedCar;
import com.baeldung.differentdi.model.fielddi.FieldBasedCar;
import com.baeldung.differentdi.model.setterdi.SetterBasedCar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Launcher of "Different Types of Bean Injection in Spring'
 */
public class SpringLauncher {

    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(
                ConstructorBasedCar.class, FieldBasedCar.class, SetterBasedCar.class, Engine.class);

        // Checking that the car class with field-based DI has its engine injected
        System.out.println(context.getBean(FieldBasedCar.class).getEngine().getType());

        // Checking that the car class with setter-based DI has its engine injected
        System.out.println(context.getBean(SetterBasedCar.class).getEngine().getType());

        // Checking that the car class with constructor-based DI has its engine injected
        System.out.println(context.getBean(ConstructorBasedCar.class).getEngine().getType());
    }

}

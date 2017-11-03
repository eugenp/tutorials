package com.baeldung.constructordi;

import com.baeldung.constructordi.domain.Car;
import com.baeldung.constructordi.domain.MotorCycle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {

        MotorCycle suzuki = getMotorCycleFromJavaConfig();

        System.out.println(suzuki);
    }

    private static MotorCycle getMotorCycleFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext((Config.class));
        return context.getBean(MotorCycle.class);
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");

        return context.getBean(Car.class);
    }

}

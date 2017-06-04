package com.baeldung.injectiontypes.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.injectiontypes.model.Car;

public class InjectionTypesTest {

    public static void main(String[] args) {
        InjectionTypesTest test = new InjectionTypesTest();

        test.TestInjectionTypes();
    }

    @Test
    public void TestInjectionTypes() {
        Car civic = getCarFromSpring("civic");

        System.out.println(civic);

        assert(civic.getBrand().equals("Honda"));
        assert(civic.getModel().equals("Civic"));
        assert(civic.getEngine()!=null);
        
        Car mustang = getCarFromSpring("mustang");

        System.out.println(mustang);
        
        assert(mustang.getBrand().equals("Ford"));
        assert(mustang.getModel().equals("Mustang"));
        assert(mustang.getEngine().getType().equals("v8"));
    }

    @SuppressWarnings("resource")
    private static Car getCarFromSpring(String beanName) {
        Car result = new ClassPathXmlApplicationContext("injectiontypescars.xml").getBean(beanName, Car.class);

        return result;
    }
}

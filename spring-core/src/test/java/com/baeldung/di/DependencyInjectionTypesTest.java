package com.baeldung.di;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionTypesTest {
    
    @Test
    public void testConstructorDI() {
        ApplicationContext context = new ClassPathXmlApplicationContext("di-beans-context.xml");
        Calculator calculator = (Calculator) context.getBean("calculator");
        assertThat(calculator.calculateAddition(5, 2), equalTo(7));
    }

    @Test
    public void testSetterDI() {
        ApplicationContext context = new ClassPathXmlApplicationContext("di-beans-context-2.xml");
        CalculatorSet calculator = (CalculatorSet) context.getBean("calculatorSet");
        assertThat(calculator.calculateAddition(3, 12), equalTo(15));
    }
   
}

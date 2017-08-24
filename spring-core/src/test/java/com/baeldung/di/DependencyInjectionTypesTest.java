package com.baeldung.di;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTypesTest {

    @Test
    public void calculateAddConstructBean_givenInputs_returnResult() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
          Config.class);
        Calculator calculator = (Calculator) context.getBean("calculator");
        assertThat(calculator.calculateAddition(5, 2), equalTo(7));
    }

    @Test
    public void calculateAddSetterBean_givenInputs_returnResult() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
          Config.class);
        CalculatorSet calculator = (CalculatorSet) context
          .getBean("calculatorSet");
        assertThat(calculator.calculateAddition(3, 12), equalTo(15));
    }

}

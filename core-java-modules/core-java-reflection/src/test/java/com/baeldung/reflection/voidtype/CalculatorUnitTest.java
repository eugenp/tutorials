package com.baeldung.reflection.voidtype;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorUnitTest {
    @Test
    void givenCalculator_whenGettingVoidMethodsByReflection_thenOnlyClearAndPrint() {
        Method[] calculatorMethods = Calculator.class.getDeclaredMethods();
        List<Method> calculatorVoidMethods = Arrays.stream(calculatorMethods)
          .filter(method -> method.getReturnType().equals(Void.TYPE))
          .collect(Collectors.toList());

        assertThat(calculatorVoidMethods)
          .allMatch(method -> Arrays.asList("clear", "print").contains(method.getName()));
    }
}
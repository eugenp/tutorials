package com.baeldung.functionpointer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

public class FunctionPointerUnitTest {

    @Test
    void givenAnonymousAddition_whenCalculate_thenReturnSum() {
        Calculator calculator = new Calculator();
        MathOperation addition = new MathOperation() {
            @Override
            public int operate(int a, int b) {
                return a + b;
            }
        };
        int result = calculator.calculate(2, 3, addition);
        assertEquals(5, result);
    }

    @Test
    void givenLambdaSubtraction_whenCalculate_thenReturnDifference() {
        Calculator calculator = new Calculator();
        MathOperation subtract = (a, b) -> a - b;
        int result = calculator.calculate(10, 4, subtract);
        assertEquals(6, result);
    }

    @Test
    void givenBiFunctionMultiply_whenApply_thenReturnProduct() {
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        int result = multiply.apply(6, 7);
        assertEquals(42, result);
    }

    @Test
    void givenBiFunctionDivide_whenCompute_thenReturnQuotient() {
        AdvancedCalculator calculator = new AdvancedCalculator();
        BiFunction<Integer, Integer, Integer> divide = (a, b) -> a / b;
        int result = calculator.compute(20, 4, divide);
        assertEquals(5, result);
    }

    @Test
    void givenMethodReference_whenCalculate_thenReturnSum() {
        Calculator calculator = new Calculator();
        MathOperation operation = MathUtils::add;
        int result = calculator.calculate(5, 10, operation);
        assertEquals(15, result);
    }

    @Test
    void givenReflection_whenInvokePower_thenReturnResult() throws Exception {
        DynamicOps ops = new DynamicOps();
        Method method = DynamicOps.class.getMethod("power", int.class, int.class);
        int result = (int) method.invoke(ops, 2, 3);
        assertEquals(8, result);
    }

    @Test
    void givenAddCommand_whenCalculate_thenReturnSum() {
        Calculator calculator = new Calculator();
        MathOperation add = new AddCommand();
        int result = calculator.calculate(3, 7, add);
        assertEquals(10, result);
    }

    @Test
    void givenEnumSubtract_whenCalculate_thenReturnResult() {
        EnumCalculator calculator = new EnumCalculator();
        int result = calculator.calculate(9, 4, MathOperationEnum.SUBTRACT);
        assertEquals(5, result);
    }
}

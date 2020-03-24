package com.baeldung.introductionjukito;

import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(JukitoRunner.class)
public class CalculatorUnitTest {

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bindMany(Calculator.class, SimpleCalculator.class, 
              ScientificCalculator.class);
            bindManyInstances(AdditionTest.class, new AdditionTest(1, 1, 2), 
              new AdditionTest(10, 10, 20), 
              new AdditionTest(18, 24, 42));
            bindManyNamedInstances(Integer.class, "even", 2, 4, 6);
            bindManyNamedInstances(Integer.class, "odd", 1, 3, 5);
        }
    }

    public static class AdditionTest {

        int a;
        int b;
        int expected;

        public AdditionTest(int a, int b, int expected) {
            this.a = a;
            this.b = b;
            this.expected = expected;
        }
    }

    @Test
    public void givenTwoNumbers_WhenAdd_ThenSumBoth(@All Calculator calc) {
        double result = calc.add(1, 1);
        assertEquals(2, result, .1);
    }

    @Test
    public void givenTwoNumbers_WhenAdd_ThenSumBoth(@All Calculator calc, 
      @All AdditionTest addTest) {
        double result = calc.add(addTest.a, addTest.b);
        assertEquals(addTest.expected, result, .1);
    }

    @Test
    public void givenEvenNumbers_whenPrint_thenOutput(@All("even") Integer i) {
        System.out.println("even " + i);
    }

    @Test
    public void givenOddNumbers_whenPrint_thenOutput(@All("odd") Integer i) {
        System.out.println("odd " + i);
    }
}

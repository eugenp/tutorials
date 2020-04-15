package com.baeldung.lambdabehave;

import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import com.insightfullogic.lambdabehave.Suite;
import com.insightfullogic.lambdabehave.generators.Generator;
import com.insightfullogic.lambdabehave.generators.SourceGenerator;
import org.junit.runner.RunWith;

@RunWith(JunitSuiteRunner.class)
public class CalculatorUnitTest {

    private Calculator calculator;

    {
        Suite.describe("Lambda behave example tests", it -> {

            it.isSetupWith(() -> {
                calculator = new Calculator(1, 2);
            });
            it.should("Add the given numbers", expect -> {
                expect.that(calculator.add()).is(3);
            });
            it.should("Throw an exception if divide by 0", expect -> {
                expect.exception(ArithmeticException.class, () -> {
                    calculator.divide(1, 0);
                });
            });
            it.uses(2, 3, 5)
              .and(23, 10, 33)
              .toShow("%d + %d = %d", (expect, a, b, c) -> {
                  expect.that(calculator.add(a, b)).is(c);
              });
            it.requires(2)
              .example(Generator.asciiStrings())
              .toShow("Reversing a String twice returns the original String", (expect, str) -> {
                  String same = new StringBuilder(str).reverse()
                    .reverse()
                    .toString();
                  expect.that(same)
                    .isEqualTo(str);
              });
            it.requires(2)
              .withSource(SourceGenerator.deterministicNumbers(5626689007407L))
              .example(Generator.asciiStrings())
              .toShow("Reversing a String twice returns the original String", (expect, str) -> {
                  String same = new StringBuilder(str).reverse()
                    .reverse()
                    .toString();
                  expect.that(same)
                    .isEqualTo(str);
              });
        });
    }
}

package com.baeldung.serenity.spring.steps;

import static com.baeldung.serenity.spring.RandomNumberUtil.randomInt;

import net.thucydides.core.annotations.Step;

/**
 * @author aiet
 */
public class AdderSteps {

    int currentNumber;
    int sum;

    @Step("given current number")
    public void givenNumber() {
        currentNumber = randomInt();
    }

    @Step("add up {0}")
    public void whenAdd(int adder) {
        sum = currentNumber + adder;
    }

    @Step("summed up")
    public void thenSummedUp() {
    }

}

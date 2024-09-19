package com.baeldung.serenity.spring.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.baeldung.serenity.spring.AdderService;

/**
 * @author aiet
 */
public class AdderConstructorDependencySteps {

    private AdderService adderService;

    public AdderConstructorDependencySteps(AdderService adderService) {
        this.adderService = adderService;
    }

    private int givenNumber;
    private int base;
    private int sum;

    public void givenBaseAndAdder(int base, int adder) {
        this.base = base;
        adderService.baseNum(base);
        this.givenNumber = adder;
    }

    public void whenAdd() {
        sum = adderService.add(givenNumber);
    }

    public void summedUp() {
        assertEquals(base + givenNumber, sum);
    }

    public void sumWrong() {
        assertNotEquals(base + givenNumber, sum);
    }

    public void whenAccumulate() {
        sum = adderService.accumulate(givenNumber);
    }
}

package com.baeldung.serenity.spring.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.serenity.spring.AdderService;

/**
 * @author aiet
 */
@ContextConfiguration(classes = AdderService.class)
public class AdderServiceSteps {

    @Autowired
    private AdderService adderService;

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

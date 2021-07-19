package com.baeldung.doublecolon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class MacbookPro extends Computer {

    private static final Logger LOG = LoggerFactory.getLogger(MacbookPro.class);

    public MacbookPro(int age, String color) {
        super(age, color);
    }

    MacbookPro(Integer age, String color, Integer healty) {
        super(age, color, healty);
    }

    @Override
    public void turnOnPc() {
        LOG.debug("MacbookPro turned on");
    }

    @Override
    public void turnOffPc() {
        LOG.debug("MacbookPro turned off");
    }

    @Override
    public Double calculateValue(Double initialValue) {

        Function<Double, Double> function = super::calculateValue;
        final Double pcValue = function.apply(initialValue);
        LOG.debug("First value is:" + pcValue);
        return pcValue + (initialValue / 10);

    }
}

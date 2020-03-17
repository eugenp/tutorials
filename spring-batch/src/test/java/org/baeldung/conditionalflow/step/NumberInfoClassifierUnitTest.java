package org.baeldung.conditionalflow.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.junit.jupiter.api.Test;

class NumberInfoClassifierUnitTest {

    @Test
    void process_convertsToInteger() throws Exception {
        NumberInfoClassifier nic = new NumberInfoClassifier();
        assertEquals(Integer.valueOf(4), nic.process(NumberInfo.from(4)));
        assertEquals(Integer.valueOf(-4), nic.process(NumberInfo.from(-4)));
    }
}
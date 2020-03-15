package org.baeldung.conditionalflow.step;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.baeldung.conditionalflow.step.NumberInfoClassifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberInfoClassifierUnitTest {

    @Test
    void process() throws Exception {
        NumberInfoClassifier nic = new NumberInfoClassifier();
        assertEquals(Integer.valueOf(4), nic.process(NumberInfo.from(4)));
        assertEquals(Integer.valueOf(-4), nic.process(NumberInfo.from(-4)));
    }
}